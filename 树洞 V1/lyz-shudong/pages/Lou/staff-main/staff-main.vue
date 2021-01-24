<!-- 
	description:后台主页
	author:lyz
	date:2020/11/8
	version:1.0
 -->

<template>
	<view class="content">
		<staffLogin style="position: relative;z-index: 1;
		margin-bottom: 20px;" @staffInfo="getStaffInfo"></staffLogin>
		<view style="position: relative;z-index: 2;" v-show="show">
			<view style="width:100%;height:100vh;position:fixed;
			background-color: #000000;top:0;left:0;" @click="show=false;curCardInfo={};">
				<popup :staffInfo="staffInfo" :curCardInfo="curCardInfo"></popup>
			</view>
		</view>
		<view style="position: relative;z-index: 0;">
			<view class="header">
				<input type="text" value="" placeholder="搜索" class="search" />
				<view class="header-second-row">
					<view class="time">
						<view class="time-icon">10</view>
						<view style="margin-left:8px;margin-right:8px;">小时</view>
						<view class="time-icon">10</view>
						<view style="margin-left:8px;">分后清空</view>
					</view>
					<!--#@#  下一步把点击滑动做出来试试-->
					<view class="icon">
						<image class="clock-icon" src="@/static/image/clock.png"></image>
						<image class="tem-icon" src="@/static/image/tem.png"></image>
					</view>
				</view>
			</view>
			<ul>
				<li v-for="(card,index) in cards" :key="index" @click="changeCurCardInfo(card)">
					<card v-bind:options="card"></card>
				</li>
			</ul>
			<view class="bottom-blank"></view>
		</view>
	</view>

</template>

<script>
	import popup from './components/popup.vue';
	import card from './components/card.vue';
	import staffLogin from './components/staffLogin.vue';
	export default {
		data() {
			return {
				pageIndex: 1,
				cardLock: false,
				cards: [],
				userInfo: this.getGlobalUserInfo(),
				show: false,
				staffInfo: {},
				curCardInfo: {}
			}
		},
		components: {
			popup,
			card,
			staffLogin
		},
		onLoad() {

		},
		mounted() {
			uni.$on("popHide", () => {
				this.show = false;
			})
			this.getCardInfo(this.pageIndex);
		},
		methods: {
			changeCurCardInfo(card) {
				this.curCardInfo = card;
				this.show = true;
				uni.$emit("getComment", this.curCardInfo.targetId);
			},
			getStaffInfo(info) {
				console.log("父组件", info);
				this.staffInfo = info;
			},
			controlDisplay() {
				this.display = !this.display;
			},
			receiveMessage(data) {
				console.log(data);
			},
			abc(a) {
				console.log(a);
			},
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
			getCardInfo(pageIndex) {
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
							if (pageIndex > res.data.data.total) {
								console.log("刷新完了");
								return;
							}
							let card = {};
							let detailedInfo = {};
							for (let i = 0; i < res.data.data.rows.length; ++i) {
								try {
									detailedInfo = JSON.parse(res.data.data.rows[i].articleContent);
								} catch (e) {
									detailedInfo = {
										nickname: "匿名",
										content: "数据库格式不同，这里接受不到！",
										color: "3px solid white",
									}
								}
								card = {
									"image": "",
									"userName": res.data.data.rows[i].nickname,
									"content": detailedInfo.content,
									"date": this.timestampToTime(res.data.data.rows[i].createDate).date,
									"time": this.timestampToTime(res.data.data.rows[i].createDate).time,
									"isAnswered": false,
									"answerText": "",
									"answerName": "",
									"like": 20,
									"dislike": 20,
									"comment": 20,
									"toUserId": res.data.data.rows[i].userId,
									"targetId": res.data.data.rows[i].id
								};
								this.cards.push(card);
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
	}
</script>

<style scoped>
	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		background-color: #333333;
	}

	.header {
		width: 325px;
		height: 100px;
	}

	.time {
		display: flex;
		justify-content: flex-start;
		align-items: flex-end;
		color: white;
		font-size: 12px;
	}

	.icon {
		display: flex;
		justify-content: flex-end;
		align-items: center;
	}

	.logo {
		height: 200rpx;
		width: 200rpx;
		margin-top: 200rpx;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 50rpx;
	}

	.text-area {
		display: flex;
		justify-content: center;
	}

	.title {
		font-size: 36rpx;
		color: #8f8f94;
	}

	.bottom-blank {
		width: 100%;
		height: 100px;
	}

	.search {
		margin-top: 10px;
		background-color: white;
		font-size: 14px;
		height: 28px;
		box-sizing: border-box;
		padding-left: 10px;
		padding-right: 10px;
		border-radius: 7px;
	}

	.header-second-row {
		width: 100%;
		height: 30px;
		margin-top: 30px;
		display: flex;
		justify-content: space-between;
	}

	.clock-icon {
		height: 30px;
		width: 45px;
		border-radius: 8px;
		margin-right: 10px;
	}

	.tem-icon {
		height: 15px;
		width: 12px;
	}

	.time-icon {
		height: 30px;
		width: 30px;
		background: url("@/static/image/block.png") center/100% 100%;
		font-size: 18px;
		font-weight: bold;
		line-height: 30px;
		text-align: center;
	}
</style>
