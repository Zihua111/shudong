<!--
	Description:
	Author:
	Date:
	Version:
-->

<template>
	<view>
		<!-- 导航栏 -->
		<uni-nav-bar shadow="false" background-color="#333333" fixed="true" status-bar="true">
			<!-- 搜索框 -->
			<uni-search-bar :radius="10" maxlength="50"></uni-search-bar>
		</uni-nav-bar>
		<view class="general-content">
			<!-- 轮播 -->
			<bw-swiper autoplay="true" :swiperList="swiperList"></bw-swiper>
			<!-- 倒计时 -->
			<camp-time></camp-time>
			<!-- 文章流 -->
			<view v-for="(card, index) in cards" :key="index">
				<view @click.stop="popUp(index)">
					<camp-card :cardInfo="card"></camp-card>
				</view>
			</view>
		</view>
		<!-- 加载更多 -->
		<view>
			<view v-if="page > totalPage" class="no-more">没有更多啦 :)</view>
			<button v-else class="button-load" hover-class="button-load-hover" @click="requestPaper(page)">加载更多</button>
		</view>
		<!-- 弹窗 -->
		<uni-popup ref="popup" type="card">
			<camp-card :cardInfo="popupCard" :comment="comment" showSubmit="true" @close="closeSubmission"></camp-card>
		</uni-popup>
	</view>
</template>

<script>
	import uniPopup from '@/components/uni-popup/uni-popup.vue'
	import CampLogin from '@/components/camp-login/camp-login.vue'
	import BwSwiper from '@/components/bw-swiper/bw-swiper.vue'
	import uniSearchBar from '@/components/uni-search-bar/uni-search-bar.vue'
	import uniNavBar from "@/components/uni-nav-bar/uni-nav-bar.vue"
	import CampCard from '@/components/camp-card/camp-card.vue'
	import CampTime from '@/components/camp-time/camp-time.vue'
	export default {
		data() {
			return {
				// 文章获取
				page: 1, // 当前页
				totalPage: 0, // 总页数
				lock: false, // 按键锁
				userInfo: this.getGlobalUserInfo(),
				serverUrl: this.$serverUrl,
				// 弹出卡片数据
				popupCard: {},
				// 弹出加载评论
				comment: {},
				// 登录按钮样式
				loginStyle: "display: flex; position: fixed; bottom: 20px; right: 20px;",
				// 轮播图源
				swiperList: [{
					img: 'https://ossweb-img.qq.com/images/lol/web201310/skin/big25011.jpg'
				}, {
					img: 'https://ossweb-img.qq.com/images/lol/web201310/skin/big25011.jpg'
				}],
				// 卡片流数据
				cards: [],
				// [{
			}
		},
		components: {
			CampCard,
			CampTime,
			uniNavBar,
			uniSearchBar,
			BwSwiper,
			CampLogin,
			uniPopup,
		},
		onShow() {
			//console.log(userUtil.getGlobalUserInfo());
			// var userInfo = {
			// 	"id": "oHx_45W_QxuBWN6vjOzKk9O9J3Y4",
			// 	"email": "x.nottingham.edu.cn",
			// 	"nickname": "NullPointerException",
			// 	"signature": null,
			// 	"createDate": 1603973653000,
			// 	"faceImg": null,
			// 	"faceImgThumb": null,
			// 	"followNum": 0,
			// 	"fansNum": 0,
			// 	"gender": null,
			// 	"major": null,
			// 	"graduationYear": null,
			// 	"degree": null,
			// 	"receiveLikeCounts": 0,
			// 	"cid": null,
			// 	"reputation": 0,
			// 	"latestLogin": null,
			// 	"follow": false
			// }
			// this.setGlobalUserInfo(userInfo);

			// 登录状态判断
			if (this.userInfo == null) {
				uni.navigateTo({
					url: '/pages/Lv/staff-login'
				});
			}

			this.requestPaper(this.page);
		},
		// 监听下拉刷新动作的执行方法，每次手动下拉刷新都会执行一次
		onPullDownRefresh() {
			this.refreshPage();
		},
		methods: {
			// 刷新页面
			refreshPage() {
				this.cards = [];
				this.page = 1;
				this.requestPaper(this.page);
			},

			// 文章弹窗
			popUp(index) {
				this.popupCard = this.cards[index];
				this.comment = {};

				if (this.popupCard.commentNum != 0) {
					this.requestComment(this.popupCard.id);
				}

				this.$refs.popup.open();
			},

			// 提交评论后窗口关闭
			closeSubmission() {
				this.refreshPage();
				this.$refs.popup.close();
			},

			// 获取评论
			requestComment(cardId) {
				var that = this;
				uni.request({
					url: that.$serverUrl + '/social/getMainComments',
					method: 'POST',
					data: {
						page: 1,
						pageSize: 10,
						type: 0,
						targetType: "ARTICLE",
						targetId: cardId,
						userId: that.userInfo.id,
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: res => {
						console.log('success');
						console.log(res);
						that.comment = res.data.data.rows[0];
					},
					fail: res => {

					}
				});
			},

			// 获取页面数据流
			requestPaper(page) {
				var that = this;
				if (!that.lock) {
					that.lock = true; //上锁
					uni.request({
						url: that.$serverUrl + '/article/queryArticles',
						method: 'POST',
						data: {
							page: page,
							pageSize: '10',
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
							that.cards = that.cards.concat(res.data.data.rows); 
							that.totalPage = res.data.data.total;
							that.lock = false; // 解锁
							that.page++;
							uni.stopPullDownRefresh(); // 停止下拉刷新动画
						},
						fail: res => {}
					});
				}
			},
		},
	}
</script>

<style>
	page {
		background-color: #333333;
	}

	bw-swiper {
		width: 650upx;
		height: 328upx;
		margin-top: 70upx;
	}

	uni-search-bar {
		padding-left: 40upx;
	}

	.no-more {
		display: flex;
		justify-content: center;
		font-size: 30upx;
		color: #e6e6e6;
		opacity: 0.8;
		letter-spacing: 5upx;
		margin: 30upx;
	}

	.button-load {
		display: flex;
		width: 200upx;
		height: 50upx;
		background: #DBA503;
		box-shadow: 0upx 3upx 24upx 0upx rgba(253, 208, 65, 0.68);
		border-radius: 10upx;
		font-size: 21upx;
		font-weight: bold;
		letter-spacing: 2upx;
		color: #FFFFFF;
		margin-top: 50upx;
		margin-bottom: 50upx;
		justify-content: center;
		align-items: center;
	}

	.button-load-hover {
		background: #FFFFFF;
		box-shadow: 0upx 3upx 24upx 0upx rgba(253, 208, 65, 0);
		color: #DBA503;
	}

	.general-content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}
</style>
