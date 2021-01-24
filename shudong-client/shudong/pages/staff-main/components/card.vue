<!-- 
	description: 后台卡片
	author:lyz
	date:2020/11/8
	version:1.0
 -->
<template>
	<view class="camp-card">
		<view class="post">
			<view class="head">
				<image src="@/static/staffMain/pentagon.png" class="card-header-image"></image>
				<view class="head-text">{{options.userName}}</view>
			</view>
			<view class="content">
				<view class="content-text">{{options.content}}</view>
			</view>
		</view>
		<view class="foot">
			<view class="card-bottom-time">{{options.date}}</view>
			<view class="card-bottom-container">
				<image src="@/static/staffMain/thumb-up.png" class="thumb-up"></image>
				<span class="thumb-up-text">20</span>
				<image src="@/static/staffMain/thumb-down.png" class="thumb-down"></image>
				<span class="thumb-down-text">20</span>
				<image src="@/static/staffMain/comment-icon.png" class="comment-icon"></image>
				<span class="comment-icon-text">20</span>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		name: 'card',
		props: {
			options: {}
		}
	}
</script>

<style>
	.camp-card {
		display: flex;
		position: relative;
		flex-direction: column;
		align-items: center;
		margin-top: 50rpx;
		margin-bottom: 50rpx;
	}

	.card-header-image {
		width: 15px;
		height: 15px;
		margin-right: 5px;
	}

	.post {
		display: flex;
		flex-direction: column;
		width: 650rpx;
		background: #444444;
		border-radius: 15rpx 15rpx 0rpx 0rpx;
	}

	.head {
		display: flex;
		flex-direction: row;
		padding-left: 30rpx;
		padding-top: 30rpx;
		justify-content: flex-start;
	}

	.head-text {
		display: flex;
		height: 27rpx;
		font-size: 21rpx;
		font-family: Microsoft YaHei;
		font-weight: bold;
		color: #888888;
		margin-top: 2px;
	}

	.content {
		display: flex;
		flex-direction: row;
		padding-top: 40rpx;
		padding-bottom: 40rpx;
	}

	.content-text {
		width: 563rpx;
		font-size: 29rpx;
		font-family: Microsoft YaHei;
		color: #FFFFFF;
		opacity: 0.8;
		line-height: 45rpx;
		letter-spacing: 2rpx;
		margin: auto;
		word-wrap: break-word;
	}

	.foot {
		display: flex;
		flex-direction: row;
		padding: 30rpx 30rpx;
		justify-content: space-between;
		width: 570rpx;
		background: #585858;
		box-shadow: 0rpx 20rpx 38rpx 0rpx rgba(219, 165, 3, 0.2) inset;
		border-radius: 0rpx 0rpx 15rpx 15rpx;
	}

	.card-bottom-container {
		width: 217.5px;
		text-align: right;
		display: inline-block;
	}

	.thumb-up {
		width: 10px;
		height: 10px;
		margin-right: 2px;
	}

	.thumb-up-text {
		width: 10px;
		height: 10px;
		font-size: 10px;
		margin-right: 10px;
		color: #DBA503;
	}

	.thumb-down {
		width: 10px;
		height: 10px;
		margin-right: 2px;
	}

	.thumb-down-text {
		width: 10px;
		height: 10px;
		font-size: 10px;
		margin-right: 10px;
		color: #D5D5D5;
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

	.card-bottom-time {
		font-size: 10px;
		color: #D5D5D5;
		width: 80px;
		display: inline-block;
		line-height: 21px;
	}
</style>
