<!-- 
	description:主页
	author:lyz
	date:2020/11/8
	version:1.0
 -->

<template>
	<view>
		<view @click="login">
			<!-- 导航栏 -->
			<uni-nav-bar background-color="rgb(34, 21, 51)" :fixed="true" :status-bar="true">
				<!-- 搜索框 -->
				<view style="display:flex;flex-direction:row;margin-left:10px;align-items: center;" @click="popSearch">
					<view @click.stop="closeSearch" :class="[{returnIcon:true},{opacity1:searchIsOpen}]"></view>
					<view class="searchIcon"></view>
					<input type="text" class="navBarSearch" :disabled="!searchIsOpen" :focus="searchIsOpen" v-model="searchValue"></input>
				</view>
			</uni-nav-bar>
			<view style="box-sizing: border-box;padding-bottom: 105px;">
				<!-- 轮播 -->
				<custom-swiper></custom-swiper>
				<view class="text">最新推送</view>
				<!-- 文章 -->
				<block v-for="(option,index) in cardInfoList" :Key="index">
					<view style="margin-bottom: 34px;">
						<card :cardInfo="option"></card>
					</view>
				</block>
				<bottom-navigation-bar></bottom-navigation-bar>
			</view>
		</view>
		<!-- login弹窗 -->
		<uni-popup ref="popLogin">
			<view style="padding-bottom: 40px;">
				<view style="color: #A17BA9;display: flex;align-items: center;flex-direction: column;">
					<view style="margin-bottom: 5px;">欢迎来到树洞</view>
					<view style="margin-bottom: 5px;">在这里</view>
					<view style="margin-bottom: 5px;">你是自由的</view>
					<view style="margin-bottom: 5px;">让树洞</view>
					<view style="margin-bottom: 15px;">成为你的声音</view>
				</view>
				<camp-login loginText="登录" @loginSuccess="closeLogin"></camp-login>
			</view>
		</uni-popup>
		<!-- search弹窗 -->
		<uni-popup ref="popSearch" :maskClick="false" :maskClass="{'position': 'fixed',
		'bottom': 0,'top': 0,'left': 0,'right': 0,'backgroundColor': 'rgb(34, 21, 51)'}">
		</uni-popup>
		<!-- 海报弹窗 -->
		<Poster></Poster>
	</view>
</template>

<script>
	import uniPopup from '@/components/uni-popup/uni-popup.vue'
	import customSwiper from '@/components/custom-swiper.vue'
	import Card from '@/components/card.vue'
	import bottomNavigationBar from '@/components/bottom-navigation-bar.vue'
	import uniNavBar from '@/components/uni-nav-bar.vue'
	import CampLogin from '@/components/camp-login/camp-login.vue'
	import Poster from '@/components/poster.vue'
	export default {
		components: {
			customSwiper,
			Card,
			bottomNavigationBar,
			uniNavBar,
			uniPopup,
			CampLogin,
			Poster
		},
		data() {
			return {
				cardInfoList: [], //保存文章流data
				pageIndex: 1, //用于分页
				cardLock: false, //锁
				searchIsOpen: false, //搜索框是否已经弹出
				searchValue: ""
			}
		},
		watch: {
			searchValue() {
				console.log(this.searchValue);
				if(this.searchValue=="yaoyao"){
					uni.redirectTo({
						url: '/pages/staff-main/staff-main'
					});
				}
			}
		},
		methods: {
			//关闭搜索框
			closeSearch() {
				if (this.searchIsOpen == true) {
					this.$refs.popSearch.close();
					this.searchIsOpen = false;
					this.searchValue = "";
				}
			},
			//关闭登录框
			closeLogin() {
				this.$refs.popLogin.close();
			},
			//判断登录
			login() {
				if (this.getGlobalUserInfo() != "" && this.getGlobalUserInfo() != null) {
					console.log('已经登录了');
					return;
				}
				console.log("弹出登录框");
				this.$refs.popLogin.open();
			},
			//弹出搜索框
			popSearch() {
				if (this.getGlobalUserInfo() == "" || this.getGlobalUserInfo() == null) {
					console.log('还未登录呢');
					return;
				}
				if (this.searchIsOpen == false) {
					console.log("弹出搜索框");
					this.$refs.popSearch.open();
					this.searchIsOpen = true;
				}
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
			//获取文章流 调用 /shudong/getArticleList 接口
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
					url: that.$serverUrl + '/shudong/getArticleList?page='+that.pageIndex+'&pageSize=10',
					method: 'GET',
					success: res => {
						console.log('success');
						console.log(res);
						if (res.data.code == 200) {
							//最后一页 刷新完了
							if (pageIndex > res.data.data.total) {
								console.log("刷新完了");
								return;
							}
							let cardInfo = {};
							for (let i = 0; i < res.data.data.rows.length; ++i) {
								cardInfo = {
									"image": "",
									"userName": res.data.data.rows[i].nickname,
									"content": res.data.data.rows[i].content,
									"date": this.timestampToTime(res.data.data.rows[i].createTime).date,
									"time": this.timestampToTime(res.data.data.rows[i].createTime).time,
									"targetId": res.data.data.rows[i].id,
									"color": res.data.data.rows[i].color,
									"staff": res.data.data.rows[i].staff,
									"comment":res.data.data.rows[i].comment,
									"staffId":res.data.data.rows[i].staffId
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
			//加载文章流
			this.getCardInfo(this.pageIndex);
		},

		//下拉刷新
		onPullDownRefresh() {
			uni.redirectTo({
				url: '/pages/user-main/user-main'
			});
		},
		//触底分页加载
		onReachBottom() {
			this.pageIndex++;
			this.getCardInfo(this.pageIndex);
		}
	}
</script>

<style>
	page {
		background-color: rgb(34, 21, 51);
	}

	.text {
		margin-top: 36px;
		margin-left: 29px;
		margin-bottom: 4.5px;
		color: #F0F3FB;
		font-size: 17px;
	}

	.navBarSearch {
		width: 200px;
		height: 28px;
		border-top-right-radius: 5px;
		border-bottom-right-radius: 5px;
		background-color: #A17BA9;
		color: rgb(34, 21, 51);
	}


	.returnIcon {
		height: 25px;
		width: 25px;
		opacity: 0;
		background: url(../../static/userMainIcon/return.png) center/100% no-repeat;
		margin-right: 5px;
		transition: opacity .3s;
	}

	.searchIcon {
		height: 28px;
		width: 28px;
		display: flex;
		justify-content: center;
		border-top-left-radius: 5px;
		border-bottom-left-radius: 5px;
		align-items: center;
		background: #A17BA9 url(../../static/userMainIcon/search.png) center/60% no-repeat;
	}

	.opacity1 {
		opacity: 1;
	}
</style>
