<!-- 
	description:主页点击小卡片后出现的弹窗
	author:lyz
	date:2020/11/8
	version:1.0
 -->
<template>
	<view class="content">
		<view class="blank-top"></view>
		<view @click.stop="">
			<!-- 卡片区 -->
			<view class="card">
				<!-- 卡片头部 -->
				<view class="card-header">
					<image src="@/static/staffMain/pentagon.png" class="card-header-image"></image>
					<span class="card-header-text">{{curCardInfo.userName}}</span>
				</view>
				<!-- 卡片内容 -->
				<view class="card-contents">{{curCardInfo.content}}</view>
				<!-- 卡片底部 -->
				<view class="card-bottom">
					<view class="card-bottom-time">{{curCardInfo.date}}&nbsp;&nbsp;&nbsp;{{curCardInfo.time}}</view>
					<view class="card-bottom-container">
						<image src="@/static/staffMain/thumb-up.png" class="thumb"></image>
						<span class="thumb-text thumb-up-color">20</span>
						<image src="@/static/staffMain/thumb-down.png" class="thumb"></image>
						<span class="thumb-text thumb-down-color">20</span>
						<image src="@/static/staffMain/comment-icon.png" class="comment-icon"></image>
						<span class="comment-icon-text">20</span>
					</view>
				</view>
			</view>
			<!-- 弧 -->
			<view class="radius"></view>
			<!-- 评论区 -->
			<view class="comment" v-if="curCardInfo.staffId!=-1">
				<view>
					<view class="comment-header">
						<image class="comment-header-image"></image>
						<span class="comment-header-text">{{curCardInfo.staffName}}</span>
					</view>
					<view class="comment-contents">{{curCardInfo.comment==null?'':curCardInfo.comment}}</view>
					<view class="card-bottom">
						<view class="card-bottom-time">05-20&nbsp;&nbsp;&nbsp;14:30</view>
						<view class="comment-bottom-container">
							<image src="@/static/staffMain/thumb-up.png" class="thumb"></image>
							<span class="thumb-text thumb-up-color">20</span>
							<image src="@/static/staffMain/thumb-down.png" class="thumb"></image>
							<span class="thumb-text thumb-down-color">20</span>
						</view>
						<view class="line"></view>
					</view>
				</view>
				<textarea maxlength="-1" class="inner-content" v-model="articleComment"></textarea>
				<button class="submit" @click="submit">发表评论</button>
			</view>
		</view>
		<!-- 用于产生弧下面的光 -->
		<!-- <view class="mask"></view> -->
	</view>
</template>

<script>
	export default {
		props: {
			curCardInfo: {}
		},
		data() {
			return {
				articleComment: '',
				lock: false
			}
		},
		methods: {
			submit() {
				if (this.lock) {
					console.log("慢慢来");
					return;
				}
				this.lock = true;
				setTimeout(() => {
					this.lock = false;
				}, 5000);
				let that = this;
				uni.request({
					url: that.$serverUrl + '/shudong/addComment',
					method: 'POST',
					data: {
						id: that.curCardInfo.targetId,
						comment: that.articleComment,
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: res => {
						if (res.data.code == 200) {
							console.log(res);
							console.log("success");
							uni.$emit("popHide");
							uni.redirectTo({
								url: '/pages/staff-main/staff-main'
							});
							that.articleComment = "";
							that.cardLock = false;
						}
					},
					fail: res => {
						console.log(res);
						console.log("fail");
					}
				});
			}
		}
	}
</script>

<style scoped>
	.content {
		width: 375px;
		height: 100%;
		background: #2F2F2F;
		opacity: 0.8;
	}

	.blank-top {
		width: 100%;
		height: 67.5px;
	}

	.card-header {
		width: 297.5px;
		height: 20px;
	}

	.card-contents {
		width: 297.5px;
		height: 80px;
		font-size: 13px;
		box-sizing: border-box;
		padding-top: 10px;
		padding-left: 15px;
		padding-right: 15px;
		margin: auto;
		color: white;
		word-wrap: break-word;
	}

	.card-bottom {
		width: 297.5px;
		height: 20px;
		margin-bottom: 10px;
	}

	.card-bottom-time {
		font-size: 10px;
		color: #D5D5D5;
		width: 80px;
		display: inline-block;
	}

	.card-header-image {
		width: 15px;
		height: 15px;
		margin-right: 5px;
	}

	.card-header-text {
		width: 80px;
		height: 20px;
		font-size: 12px;
		color: #D5D5D5;
	}

	.thumb {
		width: 10px;
		height: 10px;
		margin-right: 2px;
	}

	.thumb-text {
		width: 10px;
		height: 10px;
		font-size: 10px;
		margin-right: 10px;
	}

	.thumb-down-color {
		color: #D5D5D5;
	}

	.thumb-up-color {
		color: #DBA503;
	}

	.comment-icon {
		width: 10px;
		height: 10px;
		margin-right: 2px;

	}

	.comment-icon-text {
		width: 10px;
		height: 10px;
		font-size: 10px;
		color: #D5D5D5;
	}

	.card-bottom-container {
		width: 217.5px;
		text-align: right;
		display: inline-block;
	}

	.comment {
		position: relative;
		width: 317px;
		background: #6B6B6B;
		margin: auto;
		margin-top: -20px;
		box-shadow: 5px 5px 50px 0px rgba(219, 165, 3, 0.25) inset;
		border-radius: 0 0 15px 15px;
		box-sizing: border-box;
		padding: 20px;
		padding-top: 40px;
	}

	.card {
		position: relative;
		width: 337.5px;
		height: 135.5px;
		background: #434343;
		border-radius: 15px 15px 0 0;
		box-sizing: border-box;
		padding: 15px 15px;
		margin: auto;
		z-index: 1;
	}

	.comment-header {
		width: 277.5px;
		height: 20px;
	}

	.comment-header-image {
		width: 10px;
		height: 10px;
		margin-right: 5px;
		background: #DBA503;
	}

	.comment-header-text {
		font-size: 10px;
		width: 80px;
		height: 20px;
		color: #D5D5D5;
		margin-right: 10px;
	}

	.comment-header-text2 {
		font-size: 10px;
		width: 80px;
		height: 20px;
		color: white;
		margin-right: 10px;
	}

	.comment-contents {
		width: 277.5px;
		height: 60px;
		font-size: 12px;
		box-sizing: border-box;
		padding-top: 10px;
		color: white;
		padding-left: 5px;
		word-wrap: break-word;
	}

	.comment-bottom-container {
		width: 197.5px;
		text-align: right;
		display: inline-block;
	}

	.line {
		margin-top: 5px;
		margin-bottom: 10px;
		width: 268px;
		height: 2rpx;
		background-color: #838383;
	}

	.circle {
		width: 10px;
		height: 10px;
		background: #DBA503;
		display: inline-block;
		border-radius: 5px;
		margin-right: 10px;
	}

	.submit {
		width: 100px;
		height: 25px;
		line-height: 25px;
		background: #DBA503;
		border-radius: 5px;
		font-size: 10px;
		margin: auto;
		margin-top: 25px;
		color: white;
		font-weight: 1000;
		font-size: 12px;
	}

	.radius {
		position: relative;
		width: 337.5px;
		height: 20px;
		margin: auto;
		background: #434343;
		z-index: 1;
		border-radius: 0 0 50% 50%;
	}

	.mask {
		position: relative;
		width: 277.5px;
		height: 20px;
		margin: auto;
		margin-top: -335px;
		background: #434343;
		z-index: 0;
		border-radius: 0 0 50% 50%;
		box-shadow: 0px 10px 10px 0px rgba(219, 165, 3, 0.25);
	}

	.inner-content {
		width: 100%;
		height: 161px;
		background-color: white;
		border-radius: 10px;
		margin-top: 40px;
		padding: 10px;
		box-sizing: border-box;
	}
</style>
