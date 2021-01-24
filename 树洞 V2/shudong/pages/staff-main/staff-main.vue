<!-- 
	description:后台主页
	author:lyz
	date:2020/11/8
	version:1.0
 -->

<template>
	<view class="content">
		<view style="position: relative;z-index: 2;" v-show="show">
			<view style="width:100%;height:100vh;position:fixed;
			background-color: #000000;top:0;left:0;" @click="show=false;curCardInfo={};">
				<popup :curCardInfo="curCardInfo"></popup>
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
						<image class="clock-icon" src="@/static/staffMain/clock.png"></image>
						<image class="tem-icon" src="@/static/staffMain/tem.png"></image>
					</view>
				</view>
			</view>
			<ul>
				<li v-for="(card,index) in cardInfoList" :key="index" @click="changeCurCardInfo(card)">
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
	export default {
		data() {
			return {
				pageIndex: 1,
				cardLock: false,
				cardInfoList: [],
				userInfo: this.getGlobalUserInfo(),
				show: false,
				curCardInfo: {}
			}
		},
		components: {
			popup,
			card
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
					url: that.$serverUrl + '/shudong/getArticleList?page=' + that.pageIndex + '&pageSize=10',
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
							let staffName="";
							for (let i = 0; i < res.data.data.rows.length; ++i) {
								switch(res.data.data.rows[i].staffId){
									case '1': staffName = "大块头";break;
									case '2': staffName = "警察叔叔";break;
									case '3': staffName = "学渣";break;
									case '4': staffName = "英语老师";break;
									case '5': staffName = "邻家小妹";break;
									case '6': staffName = "小憨憨";break;
									case '7': staffName = "机器人";break;
									case '8': staffName = "班主任";break;
									default: staffName = "no";break;
								}
								cardInfo = {
									"image": "",
									"userName": res.data.data.rows[i].nickname,
									"content": res.data.data.rows[i].content,
									"date": this.timestampToTime(res.data.data.rows[i].createTime).date,
									"time": this.timestampToTime(res.data.data.rows[i].createTime).time,
									"targetId": res.data.data.rows[i].id,
									"color": res.data.data.rows[i].color,
									"staff": res.data.data.rows[i].staff,
									"comment": res.data.data.rows[i].comment,
									"staffId": res.data.data.rows[i].staffId,
									"staffName":staffName
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
		//下拉刷新
		onPullDownRefresh() {
			uni.redirectTo({
				url: '/pages/staff-main/staff-main'
			});
		},
		//触底分页加载
		onReachBottom() {
			this.pageIndex++;
			this.getCardInfo(this.pageIndex);
		}
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
		background: url("@/static/staffMain/block.png") center/100% 100%;
		font-size: 18px;
		font-weight: bold;
		line-height: 30px;
		text-align: center;
	}
</style>
