<!-- 
	description:主页
	author:lyz
	date:2020/11/8
	version:1.0
 -->

<template>
	<view @click="login">
		<!-- 导航栏 -->
		<uni-nav-bar shadow="false" background-color="#333333" fixed="true" status-bar="true">
			<!-- 搜索框 -->
			<uni-search-bar :radius="10" maxlength="50" @click.native="goSearch"></uni-search-bar>
		</uni-nav-bar>
		<view style="box-sizing: border-box;padding-bottom: 105px;">
			<!-- 轮播 -->
			<Swiper></Swiper>
			<view class="text">最新推送</view>
			<!-- 文章 -->
			<block v-for="(option,index) in cardInfoList" :Key="index">
				<view style="margin-bottom: 21px;">
					<card :cardInfo="option"></card>
				</view>
			</block>
			<Bottom></Bottom>
		</view>
	</view>
</template>

<script>
	import Swiper from './components/swiper.vue'
	import Card from './components/card.vue'
	import Bottom from './components/bottom.vue'
	import uniSearchBar from './components/uni-search-bar.vue'
	import uniNavBar from './components/uni-nav-bar.vue'
	export default {
		components: {
			Swiper,
			Card,
			Bottom,
			uniSearchBar,
			uniNavBar
		},
		data() {
			return {
				cardInfoList: [], //保存文章流data
				userInfo: this.getGlobalUserInfo(), //获取缓存中的userinfo
				pageIndex: 1, //用于分页
				cardLock: false //锁
			}
		},
		methods: {
			//判断是否已经登入
			login() {
				if (this.userInfo != "" && this.userInfo != null) {
					console.log('已经登入了');
					this.$emit('isLogin'); //如果登入了 发出信号
					return;
				};
				this.goLogin(); //去到登入页
			},
			//去到搜索页
			goSearch() {
				uni.navigateTo({
					url: '/pages/Lou/search/search'
				});
			},
			//去到登入页
			goLogin() {
				uni.navigateTo({
					url: '/pages/Lou/login/login'
				});
			},
			//转化时间戳为时间
			timestampToTime(timestamp) {
				let date = new Date(timestamp); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
				let M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
				let D = date.getDate() + ' ';
				let h = date.getHours() + ':';
				let m = date.getMinutes();
				return {
					date: M + D,
					time: h + m
				};
			},
			//获取文章流 调用 /article/queryArticles 接口
			getCardInfo(pageIndex) {
				//上锁
				if (this.cardLock) {
					console.log("慢慢来");
					return;
				}
				this.cardLock = true;
				setTimeout(() => {
					this.cardLock = false;
				}, 5000);
				let that = this;
				uni.request({
					url: that.$serverUrl + '/article/queryArticles',
					method: 'POST',
					data: {
						page: that.pageIndex,
						// pageSize: '',
						userId: that.userInfo.id,
						queryType: '0',
						orderType: '0',
						selectedTag: ""
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: res => {
						console.log('success');
						console.log(res);
						if (res.data.status == 200) {
							//最后一页 刷新完了
							if (pageIndex > res.data.data.total) {
								console.log("刷新完了");
								return;
							}
							let cardInfo = {};
							let detailedInfo = {};
							for (let i = 0; i < res.data.data.rows.length; ++i) {
								//由于一些特殊原因 数据库里的格式规范未能统一 所以做如下处理
								try {
									detailedInfo = JSON.parse(res.data.data.rows[i].articleContent);
								} catch (e) {
									detailedInfo = {
										nickname: "匿名",
										content: "数据库格式不同，这里接受不到！",
										color: "3px solid white",
									}
								}
								cardInfo = {
									"image": "",
									"userName": detailedInfo.nickname,
									"content": detailedInfo.content,
									"date": this.timestampToTime(res.data.data.rows[i].createDate).date,
									"time": this.timestampToTime(res.data.data.rows[i].createDate).time,
									"targetId": res.data.data.rows[i].id,
									"color": detailedInfo.color
								};
								this.cardInfoList.push(cardInfo);
							}
							that.cardLock = false;
						}
					},
					fail: res => {
						console.log("failed");
					}
				});
			}
		},
		mounted() {
			//接收到isLogin信号 把login这个函数重新赋值为空
			this.$once('isLogin', () => {
				this.login = () => {};
			});
			//加载文章流
			this.getCardInfo(this.pageIndex);
		},

		//下拉 分页加载
		onPullDownRefresh() {
			this.pageIndex++;
			this.getCardInfo(this.pageIndex);
		}
	}
</script>

<style>
	page {
		background-color: #232323;
	}

	.text {
		margin-top: 36px;
		margin-left: 24px;
		margin-bottom: 4.5px;
		color: white;
		opacity: 80%;
		font-size: 10px;
		font-family: MicrosoftYaHeiLight;
	}
</style>
