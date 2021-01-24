<!--  正常Import后,使用如下 
<mypicker class="major-pick-style" :range="pickerRange" :value="pickerValue" @change="pickerChange"></mypicker>
   父组件中
pickerMode  //模式，如  year, major,子组件返回新的值时会返回mode，便于父组件判断赋新值给哪个变量
pickerRange  //选择范围, 
pickerValue  //当前显示的值，为值 如2020， 非选项序号
pickerChange(res) 有动静后的回调函数，res={mode:"",newPickerValue:""}
	子组件关键值
mode:  同pickerMode
dataList:  根据 mode 自动生成相关数据，若有range, 优先使用父组件传过来的 pickerRange 
value:  接收pickerValue
pickerIndex:  选项编号，用于控制显示哪一个选项
	-->
<template>
	<view class="defaultPicker">
		<view style="height: 1000px;width: 750upx;position: fixed;left: 0;top: 0;" @tap="toggleVisible" v-if="visible"></view>

		<!-- 		<image class="arrow" src="@/static/icon/angle-down.png" mode="scaleToFill" v-if="visible" @tap="toggleVisible"></image>
 -->
		<view v-if="!visible" @click="toggleVisible" class="selectedPicker" style="text-align: left;">{{value}}</view>
		<picker-view v-if="visible" :value="pickerIndex" @change="bindChange" indicator-class="selectedPicker" class="pickerviewclass">
			<picker-view-column>
				<view class="item" v-for="(item,index) in dataList" :key="index">{{item}}</view>
			</picker-view-column>
		</picker-view>

		<!-- 		<image class="arrow" src="@/static/icon/angle-up.png" mode="scaleToFill" v-if="visible" @tap="toggleVisible"></image>
 -->
	</view>
</template>

<script>
	export default {
		name: 'mypicker',
		props: {
			mode: "", //votepicker, 
			range: "",
			value: "", //当前显示值
		},
		data() {
			return {
				dataList: "", // 对象列表
				pickerIndex: "", //显示第几个选项
				visible: false,
			}
		},
		// onBackPress() {
		// 	console.log("get")
		// },
		watch: {
			value(val, oldVal) { //普通的watch监听
				console.log('监听到爸爸变化');
				this.refresh();
			},
			range(v, ov) {
				this.refresh();
			},

		},
		mounted() {
			this.dataList = this.range;
			console.log('in picker, start console datalost, value');
			console.log(this.dataList);
			console.log(this.value);
			// debugger;
			// this.transferValueToIndex();
			// if (this.dataList[this.pickerIndex] == null || this.pickerIndex == null) {
			// 	this.pickerIndex == 0;
			// 	this.$emit('change', {
			// 		mode: this.mode,
			// 		newPickerValue: this.dataList[0],
			// 	})
			// 	console.log("picker value or pickerIndex == null, error");
			// };
		},
		methods: {
			toggleVisible() {
				this.visible = !this.visible;
			},
			transferValueToIndex() {
				console.log('值转换为index');
				console.log(this.value);
				console.log(this.dataList);
				var index = this.dataList.indexOf(this.value);
				this.pickerIndex = index;
				console.log(index);
				console.log('转换完成');
			},
			bindChange(e) {
				var val = e.detail.value;
				var res = {
					mode: this.mode,
					newPickerValue: this.dataList[e.detail.value],
				}
				console.log('ready to emit');
				console.log(res);
				this.$emit("change", res);
				// var index = this.getIndex(this.dataList, this.item);
				// console.log(index)
				// if (index != -1) {
				// 	this.value = index;	
				// }
			},
			refresh() {
				this.dataList = this.range;
				this.transferValueToIndex();
			}
		}
	}
</script>

<style scoped>
	.defaultPicker {
		position: relative;
		overflow: visible;
		z-index: 0;
		border-radius: 15upx;
	}

	.boxShadow {
		box-shadow: 0px 0px 6px rgba(0, 0, 0, 0.16);
		;
	}

	.editingPicker {
		height: 18px;
	}

	.pickerviewclass {
		display: inline-block;
		position: relative;
		height: 600upx;
		width: calc(100% - 25px);
		height: 180px;
		top: -81px;
	}

	.arrow {
		display: inline-block;
		width: 25px;
		height: 25px;
		vertical-align: bottom;
		/* position: absolute; */
		/* bottom: -3px; */
		/* right:-28px; */
	}

	.selectedPicker {
		text-align: right;
		font-size: 17px;
		height: 34px;
		line-height: 20px;
		color: #000000;
		font-weight: 800;
	}

	.item {
		text-align: left;
		font-size: 17px;
		font-weight: 800;
		/* display: inline-block;
 */
		/* 保证文章正常显示 */
		word-wrap: normal;
		word-break: keep-all;
	}
</style>
