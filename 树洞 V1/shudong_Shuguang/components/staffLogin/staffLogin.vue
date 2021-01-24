<template>
	<view>
		<view class="top-bar">
			<view class="constLine">
				<view class="logstatus" :style="stateStyle"></view>
				<view class="nickname">
					<mypicker v-if="loginState != 'logged'" :range="staffList" @change="pickerChange" class="picker-style" :mode="'nickname'" :value="loginName"></mypicker>
					<view @click="logOut" v-if="loginState == 'logged'">{{loggedInfo.nickname}}</view>
				</view>
				<view class="pswd" v-if="loginState != 'logged'"><input v-model="loginPWD" 	></view>
				<view v-if="loginState != 'logged'" @click="login()">登陆</view>
			</view>
		</view>
	</view>
</template>

<script>
	import mypicker from "@/components/mypicker.vue"
	export default {
		name: 'StaffLogin',
		data() {
			return {
				loggedInfo: "",
				staffList: [],
				loginName: "还未登录",
				loginPWD: "",
				loginState: "unlogged",
				stateStyle: "background-color:red",
			};
		},
		watch: {
			loginState(v, ov) {
				if (v == 'unlogged') {
					this.stateStyle = 'background-color:red;'
				} else if (v == 'deactive') {
					this.stateStyle = 'background-color:yellow;'
				} else if (v == 'logged') {
					this.stateStyle = "background-color:green;"
				};
				console.log(this.stateStyle);
				console.log(this.loginState);
			}
		},
		components: {
			mypicker,
		},
		created() {
			this.loadStaffList();
			var that = this;
			uni.getStorage({
				key: 'staffInfo',
				success: function(dft) {
					
					console.log(dft);
					if(dft.data != undefined){
						that.existLoginInfo(dft.data);
					}
				
				},
			});
			//debugger;
		},
		methods: {
			logOut(){
				//this.loggedInfo = "";
				this.loginState = "deactive";
				
			},
			existLoginInfo(info) {
				
				this.loggedInfo = info;
				this.loginState = 'logged';
				this.saveLogInfo(info);
			},
			saveLogInfo(info) {
				uni.setStorage({
					key:'staffInfo',
					data: info,
					success: () => {
						console.log('storge staff info success');
						this.$emit('staffId', info.id);
					}
				})
			},
			loadStaffList() {
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
							var list = res.data.data.slice(3, );
							for (var i = 0; i < list.length; i++) {
								that.staffList.push(list[i].tag);
							}
							console.log(that.staffList);

						}
					}
				});
			},
			login() {
				var that = this;
				console.log(this.loginName, this.loginPWD);
				uni.request({
					url: that.$serverUrl + '/login',
					data: {
						nickname: that.loginName,
						password: that.loginPWD,
					},
					method: 'POST',
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: (res) => {
						console.log(res);
						if (res.data.status == 200) {
							that.existLoginInfo(res.data.data);
							console.log(that.loggedInfo);
						}
					},
					fail: (res) => {
						console.log(res);
					}
				})
			},
			pickerChange(res) {
				//debugger;
				this.loginName = res.newPickerValue;
			}
		}
	}
</script>

<style scoped>
	.top-bar {
		height: 34px;
		border: 1px solid yellow;
		border-radius: 5px;
	}

	.constLine {
		height: 34px;
		width: 600upx;
		margin: 50px auto 0;

	}

	.constLine view {
		display: inline-flex;
		align-items: center;
		vertical-align: top;
	}

	.logstatus {
		width: 10px;
		height: 10px;
		border-radius: 5px;

	}

	.nickname {
		margin-left: 30px;
		width: 100px;
		height: 100%;
	}

	.picker-style {
		height: 34px;
		width: 150px;
	}

	.pswd {
		height: 32px;
		width: 100px;
		border: 1px solid #4CD964;
	}
</style>
