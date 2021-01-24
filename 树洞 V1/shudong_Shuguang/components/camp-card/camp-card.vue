<!--
	Description: card for post and reply
	Author: Desmond
	Date:
	Version:
-->

<template>
	<view class="camp-card">
		<!-- 文章主体 -->
		<view class="post">
			<view class="post-left">
				<!-- 文章头部 -->
				<view class="head">
					<image lazy-load="true" mode="aspectFit" class="pentagon" src="../../static/image/pentagon.png"></image>
					<view class="head-text">{{nickname}}</view>
				</view>
				<!-- 文章内容 -->
				<view class="content">
					<view class="content-text">{{cardInfo.articleContent}}</view>
				</view>
			</view>
			<!-- 心情状态 -->
			<view class="mood-bar" :style="{background: color}"></view>
		</view>
		<!-- 抽出部分 -->
		<view class="foot">
			<!-- 回复主体 -->
			<view class="foot-reply">
				<view v-if="showComment && cardInfo.commentNum != 0">
					<!-- 回复头部 -->
					<view class="foot-head">
						<image lazy-load="true" class="pentagon" src="../../static/image/pentagon.png"></image>
						<view class="head-text">{{comment.nickname}}</view>
					</view>
					<!-- 回复内容 -->
					<view class="foot-content">
						<view class="content-text">{{comment.comment}}</view>
					</view>
				</view>
				<!-- 提交回复表单 -->
				<form class="foot-form" @submit="submit" v-if="showSubmit">
					<textarea class="foot-textarea" maxlength="-1" show-confirm-bar="true" adjust-position="true" v-model="comment.comment"></textarea>
					<button form-type="submit" class="button-submit" hover-class="button-submit-hover">回复</button>
				</form>
			</view>
			<!-- 附加信息 -->
			<view class="foot-text">
				<!-- 时间戳 -->
				<view>{{date}}</view>
				<!-- 点赞数 -->
				<view class="foot-text-optional">
					<view class="foot-text-like">
						<image lazy-load="true" class="thumb" src="../../static/image/thumb-up.png"></image>
						{{cardInfo.likeNum}}
					</view>
					<view class="foot-text-dislike">
						<image lazy-load="true" class="thumb" src="../../static/image/thumb-down.png"></image>
						{{cardInfo.dislikeNum}}
					</view>
					<!-- 有评论则会显示该图标 -->
					<view class="foot-text-comment" v-if="cardInfo.commentNum > 0">
						<image lazy-load="true" class="thumb" src="../../static/image/comment-icon.png"></image>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		name: 'CampCard',
		props: {
			showComment: false,
			showSubmit: false,
			cardInfo: {},
			comment: {},
		},
		data() {
			return {
				// 文章获取
				userInfo: this.getGlobalUserInfo(),
				serverUrl: this.$serverUrl,
				lock: false, // 按键锁
				color: '', // 心情标签
				nickname: '' // 昵称
			}
		},
		// 时间戳转换
		computed: {
			date: function() {
				var date = new Date(this.cardInfo.createDate);
				return date.toLocaleDateString().replace(/\//g, "-") + " " + date.toTimeString().substr(0, 8);
			}
		},
		methods: {
			// 提交评论
			submit() {
				var that = this;
				if (!that.lock) {
					that.lock = true;
					uni.request({
						url: that.serverUrl + '/social/saveComment',
						method: 'POST',
						data: {
							fromUserId: that.userInfo.id,
							toUserId: that.cardInfo.userId,
							targetType: 'ARTICLE',
							targetId: that.cardInfo.id,
							comment: that.comment.comment,
						},
						header: {
							'content-type': 'application/x-www-form-urlencoded'
						},
						success: res => {
							console.log(res);
							that.lock = false;
							that.$emit('close'); // 返回关闭卡片事件
						}
					});
				}
			},
			// 解析JSON字符串
			parseJSON(str) {
				if (typeof str == 'string') {
					try {
						var obj = JSON.parse(str);
						if (typeof obj == 'object' && obj) {
							return obj;
						} else {
							return false;
						}
					} catch (e) {
						console.log('error：' + str + '!!!' + e);
						return false;
					}
				}
			}
		},
		mounted() {
			var obj;
			if ((obj = this.parseJSON(this.cardInfo.articleTitle)) != false && obj) {
				if (obj.color)
					this.color = obj.color;
				if (obj.nickname)
					this.nickname = obj.nickname;
			}
		}
	}
</script>

<style scoped>
	@import url('./camp-card.css');
</style>
