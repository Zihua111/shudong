<!--
	Description: card for create new post
	Author: Desmond
	Date:
	Version:
-->

<template>
	<view class="camp-create">

		<view class="camp-form">
			<!-- 昵称选项 -->
			<view class="nickname"><camp-switch @onChange="changeName"></camp-switch></view>
			<!-- 输入框 -->
			<view class="textarea">
				<textarea show-confirm-bar="true" adjust-position="true" placeholder="有什么想说的就写下来吧 ~" maxlength="-1" v-model="content"></textarea>
			</view>
			<!-- 标签选项 -->
			<view class="color-tag">
				<scroll-view class="scroll" scroll-x="true" scroll-left="120">
					<view style="display: inline;" v-for="(tag, index) in tagList" :Key="index">
						<view class="color-circle" :style="{border: tag}"></view>
					</view>
				</scroll-view>
			</view>
			<!-- 客服选项 -->
			<view class="staff-option">
				<scroll-view class="scroll" scroll-x="true" scroll-left="120">
					<view style="display: inline;" v-for="(staff, index) in staffList" :Key="index">
						<view class="staff-avatar">{{staff.tag}}</view>
					</view>
				</scroll-view>
			</view>
			<button class="button-submit" hover-class="button-submit-hover" @click="submit">提交</button>
		</view>

	</view>
</template>

<script>
	import CampSwitch from "../camp-switch/camp-switch.vue"
	export default {
		name: 'CampCreate',
		data() {
			return {
				// 用户信息
				userInfo: this.getGlobalUserInfo(),
				serverUrl: this.$serverUrl,
				nickname: '一颗胡萝卜',
				content: String, // 文章内容
				tagList: [
					'10px solid #FF6B81',
					'10px solid #FDCB6E',
					'10px solid #6EB6A5',
					'10px solid #6B5EB0',
					'10px solid #F65D34',
					'10px solid #0095e0',
					'10px solid #57af00'
				], // 标签样式列表
				staffList: [], // 客服列表
				lock: false, // 按键锁
			}
		},
		components: {
			CampSwitch
		},
		mounted() {
			// 获取客服列表
			var that = this;
			uni.request({
				url: that.serverUrl + '/article/getTagsList',
				method: 'POST',
				data: {},
				header: {
					'content-type': 'application/x-www-form-urlencoded'
				},
				success: res => {
					console.log("tag got");
					console.log(res);
					var temp = res.data.data;
					that.staffList = temp.slice(3);
				}
			});
		},
		methods: {
			// 提交文章
			submit() {
				var that = this;
				if (!that.lock) {
					that.lock = true;
					uni.request({
						url: that.serverUrl + '/article/uploadArticle',
						method: 'POST',
						data: {
							userId: that.userInfo.id,
							articleTitle: JSON.stringify({
								color: "#000000",
								staff: "no",
								nickname: that.nickname
							}),
							articleContent: that.content,
						},
						header: {
							'content-type': 'application/x-www-form-urlencoded'
						},
						success: res => {
							console.log(res);
							that.lock = false;
							that.$emit('close');
						}
					});
				}
			},
			changeName(nickname) {
				this.nickname = nickname;
			}
		}
	}
</script>

<style scoped>
	@import url('./camp-create.css');
</style>
