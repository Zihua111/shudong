<!-- 
	description:新建页 提交文章
	author:lyz
	date:2020/11/8
	version:1.0
 -->

<template>
	<!-- 页面 -->
	<view class="wrapper" style="background-color: #352844;padding-top: 1px;height:100vh;">
		<!-- 选择发表状态区域 -->
		<view class="styleSelection" style="background-color: #352844;">
			<!-- 黑色(匿名) -->
			<view class="blackStyle" v-show="this.isBlack" @click="changeStyle">
			</view>
			<!-- 白色(不匿名) -->
			<view class="brightStyle" v-show="!this.isBlack" @click="changeStyle"></view>
		</view>
		<!-- 正文发表 -->
		<view class="contentBox">
			<textarea style="padding: 10px;" placeholder-style="color:white;" placeholder="有什么想说的就写下来吧..." v-model="detailedInfo.content"
			 maxlength="-1" />
			<view style="color: #999999; font: 12px bold;opacity: 0.3; width: 90%; margin: 0 auto;margin-top: 25px;">
				SELECT COLOR PREFERRED
			</view>
		</view>
		<!-- 头像颜色选择 -->
		<view class="colorSelection">
			<view class="colorWrapper" >
				<block v-for="(option,index) in colorInfo" :key="index">
					<view :class="[{color:true},{current:showWhite[index]}]" :style="{border:option}" @click="getCurrentColor(index)"></view>
				</block>
			</view>
			<view style="background-image: linear-gradient(#372A46,#221A2C);color: #999999; font: 12px bold;opacity: 0.3;;margin-top: 25px;margin: 0 auto; width: 90%;">
				SELECT RESPONDENT
			</view>
		</view>
		<!-- 确认按钮 -->
		<view class="bottom">
			<view class="button" @click="submit">
				
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				userInfo: this.getGlobalUserInfo(),
				staffList: [],
				isBlack: false,
				detailedInfo: {
					"color": "13px solid #000000",
					"staff": "no",
					"nickname": "匿名",
					"content": ""
				},
				nicknameInfo:["一颗胡萝卜","一颗大白菜","俩袋鼠","一包薯片","鸡肉卷"],
				colorInfo:["13px solid #000000","13px solid #007AFF",
				"13px solid #4CD964","13px solid #FFFF00",
				"13px solid #DD524D","13px solid #DBA503",
				"13px solid #2C405A"],
				currentIndex: 0,
				showWhite:[true,false,false,false,false,false,false]
			}
		},
		methods: {
			getCurrentColor(index) {
				this.showWhite[this.currentIndex] = false;
				this.currentIndex = index;
				this.showWhite[index] = true;
			},
			changeStyle() {
				this.isBlack = !this.isBlack;
			},
			submit() {
				console.log("submit");
				console.log(this.userInfo);
				
				this.detailedInfo.color=this.colorInfo[this.currentIndex]; 
				this.detailedInfo.nickname=this.nicknameInfo[Math.floor(Math.random() * 6)];
				
				let that = this;
				uni.request({
					url: that.$serverUrl + '/article/uploadArticle',
					method: 'POST',
					data: {
						userId: that.userInfo.id,
						articleTitle: "lyz",
						articleContent: JSON.stringify(that.detailedInfo),
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: res => {
						if(res.data.status == 200) {
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
			goUserMain(){
				uni.redirectTo({
					url: '/pages/Lou/user-main/user-main'
				});
			}
		},
		created() {
			this.loadStaffList();
		}
	}
</script>

<style scoped>
	/* 选择发表状态区域 */
	.styleSelection{
		width: 90%;
		height: 23px;
		margin: 0 auto;
		margin-top: 34px;
		display: flex;
		border-radius: 15px;
		background-color:  #352844;
	}
	/* 匿名模式下的样式 */
	.styleSelection>.blackStyle{
		width: 50%;
		height: 100%;
		border-radius: 15px;
		background-color: #ffffff;
	}
	/* 发表框 */
	.contentBox{
		width: 100%;
		height: 180px;
		background-color:  #352844;
		padding-top: 20px;
	}
	/* 文本键入区域 */
	.contentBox>textarea{
		width: 90%;
		height: 134px;
		opacity: 0.15;
		border-radius: 15px;
		background-color: #453959;;
		margin: 0 auto;
		color: #F8F8F8;
	}
	/* 头像颜色选择区域 */
	.colorSelection {
		border-bottom-left-radius: 40px;
	}
	
	.colorSelection>.colorWrapper{
		text-align: center;
		white-space: nowrap;
		overflow-x: scroll;
		overflow-y: hidden;
		margin: 0 auto;
		width: 90%;
		margin-top: 20px;
	}
	/* 每一个头像的颜色 */
	.color{
		display: inline-block;
		width: 30px;
		height: 30px;
		border-radius: 50%;
		margin-left: 10px;
		white-space: nowrap;
	}
	.current{
		background-color: #FFFFFF;
	}
	/* 底部区域 */
	.bottom{
		background-color: #453959;
		width: 100%;
		height: 100%;
	}
	/* 底部按钮 */
	.bottom>.button{
		background-image: linear-gradient(to right, #7C749F, #50456E);
		width: 170px;
		height: 45px;
		opacity: 0.48;
		margin: 0 auto;
		border-radius: 20px;
		position: relative;
		top: 150px;
	}
</style>
