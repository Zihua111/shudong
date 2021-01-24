<!-- 
	description: 主页卡片
	author:lyz
	date:2020/11/8
	version:1.0
 -->

<template>
	<view style="margin-left: 23.5px;margin-right: 26.5px;">
		<!-- 卡片部分 -->
		<view class="cardBox" @click="show">
			<view style="display: flex;flex-direction: row;">
				<view style="width: 280px;box-sizing: border-box;padding: 13px 23px;padding-right: 0;">
					<!-- 卡片头部 -->
					<view style="display: flex; align-items: center;">
						<!-- image -->
						<image :src="cardInfo.image" mode="aspectFill" style="margin-right:10px;"></image>
						<!-- userName -->
						<text class="text1">{{cardInfo.userName}}</text>
					</view>
					<!-- content -->
					<view class="content text2">{{cardInfo.content}}</view>
					<!-- 卡片底部 -->
					<view style="display: flex; align-items: center;">
						<text class="text1" style="margin-right:10px;">{{cardInfo.date}}</text>
						<text class="text1">{{cardInfo.time}}</text>
					</view>
				</view>
				<!-- 右边的一段有颜色的弧 颜色是从数据库里取出来的 -->
				<view class="radius" :style="{borderRight:computedColor}"></view>
			</view>
		</view>
		<!-- 判断有无评论 -->
		<view style="color:white;font-size: 20px;" v-if="commentInfo.comment != ''">有评论</view>
		<view style="display: flex;justify-content: center;" @click="show">
			<view v-if="cardInfo.isAnswered" class="answer"></view>
		</view>
		<!-- 弹窗部分 -->
		<view class="pop" v-show="isShow" @click="hide">
			<view style="position: relative;top:200px;" @click.stop="">
				<!-- 弹窗的卡片部分 -->
				<view class="cardBox">
					<view style="display: flex;flex-direction: row;">
						<view style="width: 280px;box-sizing: border-box;padding: 13px 23px;padding-right: 0;">
							<!-- 卡片头部 -->
							<view style="display: flex; align-items: center;">
								<image :src="cardInfo.image" mode="aspectFill" style="margin-right:10px;"></image>
								<text class="text1">{{cardInfo.userName}}</text>
							</view>
							<!-- content -->
							<view class="content text2">{{cardInfo.content}}</view>
							<!-- 卡片底部 -->
							<view style="display: flex; align-items: center;">
								<text class="text1" style="margin-right:10px;">{{cardInfo.date}}</text>
								<text class="text1">{{cardInfo.time}}</text>
							</view>
						</view>
						<!-- 右边的一段有颜色的弧 颜色是从数据库里取出来的 -->
						<view class="radius" :style="{borderRight:computedColor,borderBottomRightRadius: 0}"></view>
					</view>
				</view>
				<!-- 评论部分 -->
				<view style="display: flex;justify-content: center;">
					<view v-if="commentInfo.comment != ''" class="answerBox">
						<!-- 评论头部 -->
						<view style="display: flex; align-items: center;">
							<image :src="cardInfo.image" mode="aspectFill" style="margin-right:10px;"></image>
							<text class="text1" style="color:black;">{{commentInfo.nickname}}</text>
						</view>
						<!-- 评论内容 -->
						<view class="content text2" style="color:black;">{{commentInfo.comment}}</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props: {
			cardInfo: {}  //文章流相关内容
		},
		computed: {
			//判断传回来的cardInfo.color属性是否为空
			computedColor: function() {
				if (this.cardInfo.color == "" || this.cardInfo.color == undefined) {
					return "3px solid #515151";
				} else {
					return this.cardInfo.color.substring(1);
				}
			}
		},
		data() {
			return {
				isShow: false,     //控制弹窗的hide和show
				commentInfo: {     //评论信息 默认为空
					nickname: "",
					comment: ""
				}
			}
		},
		methods: {
			//控制弹窗的hide和show
			show() {
				this.isShow = true;
			},
			hide() {
				this.isShow = false;
			},
			//get comment 调用 /social/getMainComments 接口
			getComment() {
				let that = this;
				uni.request({
					url: that.$serverUrl + '/social/getMainComments',
					method: 'POST',
					data: {
						page: 1,
						pageSize: 10,
						type: 0,
						targetType: "ARTICLE",
						targetId: that.cardInfo.targetId
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded'
					},
					success: res => {
						console.log('get comment success');
						console.log(res);
						if (res.data.status == 200) {
							that.commentInfo = res.data.data.rows[0];
						}
					},
					fail: res => {

					}
				});
			}
		},
		mounted() {
			this.getComment();
		}
	}
</script>

<style scoped>
	.cardBox {
		width: 100%;
		background: #515151;
		border-radius: 10px;
	}

	image {
		width: 24px;
		height: 24px;
		border-radius: 12px;
		background-color: #ffffff;
	}

	.text1 {
		color: #888888;
		font-size: 10px;
		text-align: justify;
	}

	.text2 {
		color: #E2E2E2;
		font-size: 15px;
		text-align: justify;
	}

	.content {
		padding: 10px 0;
		box-sizing: border-box;
		width: 100%;
		word-wrap: break-word;
	}

	.radius {
		width: 23px;
		border-top-right-radius: 10px;
		border-bottom-right-radius: 10px;
		border-right: 3px solid white;
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
</style>
