import axios from 'axios'
import http from '../http.js'

let base = ''
// 登录管理
export const requestLogin = params => { return axios.post(`${base}/signin`, params).then(res => res.data) }

// 用户管理

export const getUserListPage = params => { return http.get(`${base}/super/users`, { params: params }).then(res => res.data) }

export const adUser = params => { return http.post(`${base}/super/users`, params).then(res => res.data) }

export const showRoleSet = params => { return http.get(`${base}/role`).then(res => res.data) }

export const saveRoleSet = params => { return http.put(`${base}/super/users`, params).then(res => res.data) }

// 权限管理
export const addMenuList = params => { return http.post(`${base}/menuAdd`, params).then(res => res.data) }

export const editMenuList = params => { return http.put(`${base}/menuEdit`, params).then(res => res.data) }

// 角色管理
export const requestRolesList = params => { return http.get(`${base}/role`, { params: params }).then(res => res.data) }

export const roleMenuDelete = params => { return http.delete(`${base}/roleMenuDelete`, { params: params }).then(res => res.data) }

export const getMenuTree = params => { return http.get(`${base}/student/add`).then(res => res.data) }

export const roleMenuListAdd = params => { return http.post(`${base}/roleMenuAdd`, params).then(res => res.data) }


export const editRole = params => { return http.put(`${base}/role`, params).then(res => res.data) }

// 课程管理
export const getCourseListPage = params => { return http.get(`${base}/course/listpage`, { params: params }) }

export const editCourse = params => { return http.get(`${base}/course/edit`, { params: params }) }

export const addCourse = params => { return http.get(`${base}/course/add`, { params: params }) }

export const removeCourse = params => { return http.get(`${base}/course/remove`, { params: params }) }

export const batchRemoveCourse = params => { return http.get(`${base}/course/batchremove`, { params: params }) }

// 字典管理
export const addItems = params => { return http.post(`${base}/insertTextData`, { params: params }).then(res => res.data) }

export const editDic = params => { return http.put(`${base}/dataDictionary`, { params: params }).then(res => res.data) }

export const subEditDic = params => { return http.post(`${base}/insertAllTextData`, params).then(res => res.data) }

export const getDicListPage = params => { return http.get(`${base}/dataDictionary`, { params: params }).then(res => res.data) }

export const editDictionary = params => { return http.put(`${base}/dataDictionary`, { params: params }).then(res => res.data) }// 测一下上面的是不是可以复用

// 菜单管理

export const addMenus = params => { return http.post(`${base}/menuAdd`, { params: params }).then(res => res.data) }// 上面有可复用

export const editMenus = params => { return http.put(`${base}/menuEdit`, params).then(res => res.data) }// 上面有可复用

// 组织管理
export const getSholl = params => { return http.get(`${base}/structure/orgs/schools?page=1&pageSize=10`).then(res => res.data) }

export const addSchools = params => { return http.post(`${base}/structure/orgs/schools`, { params: params }).then(res => res.data) }

export const addColleges = params => { return http.post(`${base}/structure/orgs/colleges`, { params: params }).then(res => res.data) }

export const addMajors = params => { return http.post(`${base}/structure/orgs/majors`, { params: params }).then(res => res.data) }

// 参数管理
export const getParamsListPage = params => { return http.get(`${base}/params/class`, { params: params }).then(res => res.data) }

export const addParam = params => { return http.post(`${base}/params/class`, { params: params }).then(res => res.data) }

export const editParams = params => { return http.put(`${base}/params/class`, params).then(res => res.data) }
