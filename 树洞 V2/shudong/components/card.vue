<!-- 
	description: 主页卡片
	author:lyz
	date:2020/11/8
	version:1.0
 -->

<template>
	<view style="margin-left: 23.5px;margin-right: 26.5px;" @click="clickCard">
		<!-- 卡片部分 -->
		<view class="cardBox">
			<view style="display: flex;flex-direction: row;">
				<view style="width: 280px;box-sizing: border-box;padding: 13px 23px;padding-right: 0;">
					<!-- 卡片头部 -->
					<view style="display: flex; align-items: center;">
						<!-- image -->
						<image :src="cardInfo.image" mode="aspectFill" :style="{background:computedColor}"></image>
						<!-- userName -->
						<text class="text1">{{cardInfo.userName}}</text>
					</view>
					<!-- content -->
					<view class="content text2">{{cardInfo.content}}</view>
					<!-- 卡片底部 -->
					<view style="display: flex; align-items: center;">
						<text class="text1" style="margin-right:10px;">{{cardInfo.date}}</text>
						<text class="text1" style="padding: 0 10px;">{{cardInfo.time}}</text>
					</view>
				</view>
				<!-- 右边的一段有颜色的线 颜色是从数据库里取出来的 -->
				<view class="radius" :style="{borderRight:computedBorderColor}"></view>
			</view>
		</view>
		<!-- 判断有无评论 -->
		<view :class="[{cardHasComment:true,height30:!cardClicked,padding24:cardClicked}]" v-if="cardInfo.comment != null">
			<view v-show="cardClicked" :class="{animated:true,fadeIn:cardClicked,fadeOut:!cardClicked}">
				<!-- 评论头部 -->
				<view style="display: flex; align-items: center;position: relative;">
					<image :src="cardInfo.staff.slice(3)" mode="aspectFill" :style="{background:computedStaffColor}"></image>
					<text class="text1">{{staffName}}</text>
					<view @click.stop="" class="share" hover-class="shareHover"></view>
				</view>
				<!-- 评论内容 -->
				<view class="content text2">{{cardInfo.comment}}</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props: {
			cardInfo: {
				"color": ""
			} //文章流相关内容
		},
		computed: {
			//判断传回来的cardInfo.color属性是否为空
			computedColor: function() {
				if (this.cardInfo.color != "" && this.cardInfo.color != undefined) {
					return this.cardInfo.color;
				}
				return "white";
			},
			computedBorderColor: function() {
				if (this.cardInfo.color != "" && this.cardInfo.color != undefined) {
					return "3px solid " + this.cardInfo.color;
				}
				return "3px solid white";
			},
			computedStaffColor: function() {
				return "#6E5F85";
			}
		},
		data() {
			return {
				isShow: false, //控制弹窗的hide和show
				cardClicked: false,
				staffName:""
			}
		},
		created() {
			console.log(this.cardInfo);
		},
		methods: {
			//分享海报
			sharePoster() {
				if (this.getGlobalUserInfo() == "" || this.getGlobalUserInfo() == null) {
					console.log('还未登录呢');
					return;
				}
				console.log("分享海报");
				let posterInfo = {};
				posterInfo.nickname = this.staffName;
				posterInfo.comment = this.cardInfo.comment;
				posterInfo.staffIcon = this.cardInfo.staff.slice(3);
				uni.$emit("share", posterInfo);
			},
			//点击卡片评论下拉展示
			clickCard() {
				if (this.getGlobalUserInfo() == "" || this.getGlobalUserInfo() == null) {
					console.log('还未登入呢');
					return;
				};
				this.cardClicked = !this.cardClicked;
			}
		}
	}
</script>

<style scoped>
	@import url('../common/animate.min.css');

	.cardBox {
		width: 100%;
		background: #463959;
		border-radius: 10px;
	}

	image {
		width: 24px;
		height: 24px;
		border-radius: 12px;
		margin-right: 10px;
	}

	.text1 {
		color: #CACED9;
		font-size: 10px;
		text-align: justify;
	}

	.text2 {
		color: #F0F3FB;
		font-size: 15px;
		text-align: justify;
	}

	.content {
		padding: 10px 10px;
		box-sizing: border-box;
		width: 100%;
		word-wrap: break-word;
	}

	.radius {
		width: 23px;
		margin-top: 20px;
		margin-bottom: 20px;
		border-radius: 2px;
	}

	.answer {
		background: white;
		height: 27px;
		width: 287px;
		border-bottom-left-radius: 5px;
		border-bottom-right-radius: 5px;
	}

	.answerBox {
		background: white;
		width: 287px;
		border-bottom-left-radius: 5px;
		border-bottom-right-radius: 5px;
		background: grey;
		box-sizing: border-box;
		padding: 21px;
	}

	.pop {
		position: fixed;
		top: 0;
		left: 0;
		height: 100%;
		width: 100%;
		background: rgba(0, 0, 0, 0.9);
		box-sizing: border-box;
		padding-left: 23.5px;
		padding-right: 26.5px;
	}

	.cardHasComment {
		background-color: #6E5F85;
		margin: 0 24px;
		border-bottom-left-radius: 8px;
		border-bottom-right-radius: 8px;
		box-shadow: 0px 8px 16px 0px rgba(38, 41, 74, 0.66) inset;
		box-sizing: border-box;
		padding-left: 24px !important;
		padding-right: 24px !important;
		transition: all 1s;
	}

	.height30 {
		height: 30px !important;
	}

	.padding24 {
		padding-top: 24px !important;
		padding-bottom: 8px !important;
	}

	.share {
		position: absolute;
		right: 0px;
		height: 30px;
		width: 30px;
		background: url(../static/userMainIcon/share.png) center/100% no-repeat;
		transition: all 500ms;
		border-radius: 50%;
	}

	.shareHover {
		box-shadow: 0rpx 0rpx 15rpx 0rpx rgba(255, 255, 255, 0.8);
	}
</style>
