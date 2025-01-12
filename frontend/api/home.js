import request from "@/utils/request";

//获取公告
export function getNotice(){
	return request({
		url: '/manage/notice',
		method: 'get',
	})
}
//获取团队虚拟币
export function getCoin(){
	return request({
		url: '/manage/getCoin',
		header:{
			isToken: true
		},
		method: 'get'
	})
}
//获取虚拟币交易记录
export function getRecords(){
	return request({
		url: '/manage/getRecord',
		header:{
			isToken: true
		},
		method: 'get'
	})
}