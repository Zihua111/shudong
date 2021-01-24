<!-- 
description: 微信登陆登录组件，引用可绑定:loginStyle     :loginText,
	定义样式或者内容;  成功/失败返回 @loginSuccess/@loginFail 事件
author: Yaoyao
DATE: 29.10.2020 -->


<template>
	<view>
		<button :style="loginStyle" open-type="getUserInfo" class="loginButton" @click="loadInfo">{{loginText}}</button>
	</view>
</template>

<script>
	import {
		encryptDes,
		decryptDes
	} from '@/common/des.js'

	export default {
		name: 'CampLogin',
		props: {
			loginStyle: "",
			loginText: {
				default: "我欠点",
			},
		},
		data() {
			return {
				serverUrl: this.$serverUrl,
				encoded: "GLKS62vox+Zzm1oZ2AuE6bxBUQFdniwD22i7Rshn6UP2cMyaJAdXog==",
				tagList: [{
						id: "0",
						tag: "wxceee7651a4bd8583"
					},
					{
						id: "1",
						tag: "happygraduation"
					}
				],
			}
		},

		mounted() {
			this.loadKey();
		},

		methods: {
			loadKey() {
				var that = this;
				uni.request({
					url: that.$serverUrl + '/article/getTagsList',
					method: 'POST',
					data: {},
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: res => {
						if (res.data.status == 200) {
							//that.tagList = res.data.data;
							console.log("tagList", that.tagList);
						}
					}
				});
			},

			loadInfo() {
				if (this.getGlobalUserInfo() != "" && this.getGlobalUserInfo() != null) {
					console.log('缓存已经存在个人信息');
					this.$emit('loginSuccess');
					return;
				};
				console.log(123, this.getGlobalUserInfo());
				var that = this;
				uni.login({
					success: res_login => {
						uni.getUserInfo({
							success: info => {
								console.log('info');
								console.log(info);
								var u = "https://api.weixin.qq.com/sns/jscode2session?appid=" + that.tagList[0].tag + "&secret=" +
									decryptDes(
										that.encoded, that.tagList[1].tag) + "&js_code=" + res_login.code + "&grant_type=authorization_code";
								//console.log(u);
								uni.request({
									url: u,
									success: (openid) => {
										console.log('openid');
										console.log(openid);
										this.receiveInfo(info, openid);
									},

								})


							} //getUserInfo.success
						}) //uni.getUserInfo
					}
				})
			}, //login
			receiveInfo(info, openid) {
				//debugger;
				var that = this;
				var user = Object.assign(info.userInfo, {
					id: openid.data.openid
				})
				console.log(user);
				that.setGlobalUserInfo(user);//修正妖妖的代码
				//debugger;
				uni.request({
					url: this.$serverUrl + '/regist',
					method: "POST",
					data: {
						id: user.id,
						nickname: user.nickname,
						password: "",
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: (res) => {
						console.log("success", res);
						if (res.data.status == 200) {
							that.setGlobalUserInfo(res.data.data);
							that.$emit('loginSuccess');
						}
					},
					fail: (res) => {
						console.log("fail", res);
						setTimeout(that.loadInfo(), 500);
						that.$emit('loginFail');
					}
				})
				//this.setGlobalUserInfo()
			},
			decode() {
				decryptDes(encoded, key);
			},
		}
	}
</script>

<style scoped>
	.loginButton {
		width: 72px;
		height: 30px;
		background: #FCC041;
		border-radius: 4px;
		font-size: 14px;
		line-height: 30px;
	}
</style>
<!-- url: "https://api.weixin.qq.com/sns/jscode2session?appid=" + that.tagList[0] + "&secret=" + decryptDes(that.encoded,that.tagList[1]) + "&js_code=" + res_login.code + "&grant_type=authorization_code",
 -->
