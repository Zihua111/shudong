<!-- 
	description:新建页 提交文章
	author:lyz
	date:2020/11/8
	version:1.0
 -->

<template>
	<!-- 页面 -->
	<view style="height:100vh;background-color:#352844;">
		<view style="background-color: #221533; padding-top: 34px;box-sizing: border-box;">
			<!-- 选择发表状态区域 -->
			<camp-switch @onChange="changeName"></camp-switch>
			<!-- 正文发表 -->
			<view class="contentBox">
				<textarea style="padding: 10px;" placeholder-style="color: #FFFFFF;" placeholder="有什么想说的就写下来吧..." v-model="detailedInfo.content"
				 maxlength="-1" />
				</view>
		</view>
		<!-- 头像颜色选择 -->
			<view class="colorSelection">
				<view class="styleTitle">
					SELECT COLOR PREFERRED
				</view>
				<view style="width: 100%;">
					<view class="colorWrapper">
						<block v-for="(option,index) in borderColorInfo" :key="index">
							<view :class="[{color:true},{current:showWhite[index]}]" :style="{border:option}" @click="getCurrentColor(index)"></view>
						</block>
					</view>
				</view>
			</view>
			<!-- 客服选择 -->
			<view class="colorSelection">
				<view class="styleTitle2">
					SELECT RESPONDENT
				</view>
				<view class="colorWrapper">
					<block v-for="(option,index) in staffIconInfo" :key="index">
						<image :src="option" mode="aspectFit" :class="[{color2:true},{current:showWhite2[index]}]" @click="getCurrentColor2(index)"></image>
					</block>
				</view>
			</view>
			<!-- 确认按钮 -->
			<view class="bottom">
				<view class="button" @click="submit" hover-class="buttonHover">
					{{langCn.send}}
				</view>
			</view>
		</view>
</template>

<script>
	import campSwitch from '../../components/camp-switch.vue'
	import En from '../../common/lang/en.js'
	import Cn from '../../common/lang/cn.js'
	export default {
		components: {
			campSwitch
		},
		data() {
			return {
				userInfo: this.getGlobalUserInfo(),
				staffList: [],
				detailedInfo: {
					"color": "#000000",
					"staff": "no",
					"nickname": "匿名",
					"content": ""
				},
				borderColorInfo: [
					"13px solid #000000", "13px solid #007AFF",
					"13px solid #4CD964", "13px solid #FFFF00",
					"13px solid #DD524D", "13px solid #DBA503",
					"13px solid #2C405A"
				],
				colorInfo: [
					"#000000", "#007AFF",
					"#4CD964", "#FFFF00",
					"#DD524D", "#DBA503",
					"#2C405A"
				],
				currentIndex: 0,
				currentIndex2: 0,
				showWhite: [true, false, false, false, false, false, false],
				showWhite2: [true, false, false, false, false, false, false, false,false,false],
				staffIconInfo: [
					"../../static/staffIcon/0.png",
					"../../static/staffIcon/00.png",
					"../../static/staffIcon/1.png",
					"../../static/staffIcon/2.png",
					"../../static/staffIcon/3.png",
					"../../static/staffIcon/4.png",
					"../../static/staffIcon/5.png",
					"../../static/staffIcon/6.png",
					"../../static/staffIcon/7.png",
					"../../static/staffIcon/8.png",
				]
			}
		},
		computed:{
			langCn(){
				return Cn; //切换中文
			},
			langEn(){
				return En; //切换英文
			}
		},
		methods: {
			changeName(nickname){
				this.detailedInfo.nickname = nickname;
				console.log(nickname);
			},
			getCurrentColor2(index) {
				this.showWhite2[this.currentIndex2] = false;
				this.currentIndex2 = index;
				this.showWhite2[index] = true;
			},
			getCurrentColor(index) {
				this.showWhite[this.currentIndex] = false;
				this.currentIndex = index;
				this.showWhite[index] = true;
			},
			submit() {
				console.log("submit");
				console.log(this.userInfo);
				
				this.detailedInfo.color = this.colorInfo[this.currentIndex];
				this.detailedInfo.staff = this.staffIconInfo[this.currentIndex2];
				if(this.currentIndex2==0){
					this.detailedInfo.staff="no";
				}else if (this.currentIndex2==1){
					this.currentIndex2 = Math.floor(Math.random()*8+2);
					this.detailedInfo.staff=this.staffIconInfo[this.currentIndex2];
				}
				
				let that = this;
				uni.request({
					url: that.$serverUrl + '/shudong/createArticle',
					method: 'POST',
					data: {
						userId: that.userInfo.id,
						staffId: that.currentIndex2-1,
						staff:that.detailedInfo.staff,
						nickname:that.detailedInfo.nickname,
						title:"shudong",
						color:that.detailedInfo.color,
						content: that.detailedInfo.content,
					},
					success: res => {
						if (res.data.code == 200) {
							console.log(res);
							console.log("success");
							this.goUserMain();
						}
					},
					fail: res => {
						console.log(res);
						console.log("fail");
					}
				});
			},
			loadStaffList() {
				var that = this;
				uni.request({
					url: that.$serverUrl + '/shudong/getAdminList',
					method: 'GET',
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: res => {
						if (res.data.code == 200) {
							that.staffList=res.data.data;
							console.log(that.staffList);
						}
					}
				});
			},
			goUserMain() {
				uni.redirectTo({
					url: '/pages/user-main/user-main'
				});
			}
		},
		created() {
			this.loadStaffList();
		}
	}
</script>

<style scoped>
	/*  选择发表状态区域 */
	.styleSelection {
		position: relative;
		width: 82%;
		height: 23px;
		line-height: 23px;
		margin: 0 auto;
		display: flex;
		border-radius: 15px;
		background-color: #352844;
		font-size: 13px;
	}

	/* 匿名模式下的样式 */
	.blackStyle,
	.brightStyle {
		position: absolute;
		color: #F2F2F2;
		width: 50%;
		height: 100%;
		border-radius: 15px;
		background-color: #453959;
		text-align: center;
	}

	/* 发表框 */
	.contentBox {
		width: 100%;
		height: 180px;
		background-color: #221533;
		padding-top: 20px;
		margin-top: 30px;
	}

	/* 文本键入区域 */
	.contentBox>textarea {
		width: 77%;
		height: 134px;
		opacity: 0.5;
		border-radius: 15px;
		background-color: #453959;
		margin: 0 auto;
		color: #F8F8F8;
	}

	/*选择框标题*/
	.styleTitle {
		background: linear-gradient(#221533, rgba(0, 0, 0, 0.5));
		color: #352844;
		font-size: 19px;
		font-weight: 600;
		height: 30px;
		margin: 0 auto;
		padding-left: 40px;
		padding-top: 10px;
		border-bottom-left-radius: 50px;
		box-sizing: border-box;
	}

	.styleTitle2 {
		background: linear-gradient(#352844, rgba(0, 0, 0, 0.5));
		color: #352844;
		font-size: 19px;
		font-weight: 600;
		height: 30px;
		margin: 0 auto;
		padding-left: 40px;
		padding-top: 10px;
		border-bottom-left-radius: 50px;
		box-sizing: border-box;
	}

	.colorWrapper {
		text-align: center;
		white-space: nowrap;
		overflow-x: scroll;
		overflow-y: hidden;
		margin: 0 auto;
		width: 90%;
		padding-top: 20px;
		box-sizing: border-box;
	}

	/* 每一个头像的颜色 */
	.color {
		display: inline-block;
		width: 28px;
		height: 28px;
		border-radius: 50%;
		margin: 10px;
		white-space: nowrap;
		transition: all 500ms;
	}

	.color2 {
		display: inline-block;
		width: 50px;
		height: 50px;
		margin: 10px;
		white-space: nowrap;
		transition: all 500ms;
		border-radius: 50%;
	}

	.current {
		box-shadow: 0rpx 0rpx 15rpx 0rpx rgba(255, 255, 255, 0.8);
	}

	/* 底部区域 */
	.bottom {
		width: 100%;
	}

	/* 底部按钮 */
	.button {
		background-image: linear-gradient(to right, #7C749F, #50456E);
		width: 230px;
		height: 45px;
		text-align: center;
		line-height: 45px;
		font-size: 25px;
		font-weight: 400;
		color: #221533;
		opacity: 0.48;
		margin: 0 auto;
		border-radius: 20px;
		position: relative;
		margin-top: 70px;
		box-shadow: 0rpx 0rpx 15rpx 0rpx rgba(255, 255, 255, 0.8);
		transition: all 500ms;
	}

	.buttonHover {
		font-size: 26px;
		opacity: 0.64;
	}
</style>
