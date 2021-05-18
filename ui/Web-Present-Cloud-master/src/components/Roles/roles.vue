<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>角色管理</el-breadcrumb-item>
      <el-breadcrumb-item>角色列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图 -->
    <el-card>
      <!-- 添加角色按钮 -->
      <el-row>
        <el-col>
          <el-button type="primary" @click="AddRoleDialogVisible=true">添加角色</el-button>
        </el-col>
      </el-row>
      <!-- 角色列表 -->
      <el-table :data="rolesList" border stripe>
        <el-table-column label="#" type="index"></el-table-column>
        <el-table-column label="角色名称" prop="roleName"></el-table-column>
        <el-table-column label="角色描述" prop="roleDescription"></el-table-column>
        <el-table-column label="操作" width="410px">
          <template slot-scope="scope">
            <el-button
              type="primary"
              icon="el-icon-edit"
              size="mini"
              @click="showEditDialog(scope.row.id)"
            >编辑</el-button>
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              @click="removeRoleById(scope.row.id)"
            >删除</el-button>
            <el-button
              type="warning"
              icon="el-icon-setting"
              size="mini"
              @click="showSetRightDialog(scope.row)"
            >分配权限</el-button>
            <el-button
              type="warning"
              icon="el-icon-delete"
              size="mini"
              @click="showDelRightDialog(scope.row)"
            >删除权限</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <!-- 添加角色对话框 -->
    <el-dialog
      title="添加角色"
      :visible.sync="AddRoleDialogVisible"
      width="40%"
      @close="addRoleDialogClosed"
    >
      <el-form
        :model="addRoleForm"
        ref="addRoleFormRef"
        :rules="addRoleFormRules"
        label-width="100px"
      >
        <el-form-item label="角色码" prop="roleCode">
          <el-input v-model="addRoleForm.roleCode"></el-input>
        </el-form-item>
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="addRoleForm.roleName"></el-input>
        </el-form-item>
        <el-form-item label="角色描述" prop="roleDescription">
          <el-input v-model="addRoleForm.roleDescription"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="AddRoleDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addRoles">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 编辑角色对话框 -->
    <el-dialog
      title="编辑角色"
      :visible.sync="editRoleDialogVisible"
      width="40%"
      @close="addRoleDialogClosed"
    >
      <el-form
        :model="editRoleForm"
        ref="editRoleFormRef"
        :rules="editRoleFormRules"
        label-width="100px"
      >
        <el-form-item label="角色码" prop="roleCode">
          <el-input v-model="editRoleForm.roleCode"></el-input>
        </el-form-item>
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="editRoleForm.roleName"></el-input>
        </el-form-item>
        <el-form-item label="角色描述" prop="roleDescription">
          <el-input v-model="editRoleForm.roleDescription"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editRoleDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editRoles">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 分配权限对话框 -->
  <el-dialog
    title="分配权限"
    :visible.sync="setRightDialogVisible"
    width="50%"
    @close="setRightDialogClosed">
    <el-tree :data="rightsList" :props="treeProps"     show-checkbox
    node-key="id" default-expand-all :default-checked-keys="defKeys" ref="treeRef"></el-tree>
    <span slot="footer" class="dialog-footer">
      <el-button @click="setRightDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="allotRights">确 定</el-button>
    </span>
  </el-dialog>

  <!-- 删除权限对话框 -->
  <el-dialog
    title="删除权限"
    :visible.sync="delRightDialogVisible"
    width="80%">
    <template >
      <el-row :class="['bdbottom', i1 === 0 ? 'bdtop' : '', 'vcenter']" v-for="(item1, i1) in this.roleMenu" :key="item1.id" vcenter>
        <el-col :span="5">
          <el-tag closable @close="removeRightById(item1.id)">{{item1.menuName}}</el-tag>
          <i class="el-icon-caret-right"></i>
        </el-col>
        <el-col :span="6">
          <el-row v-for="(item2, i2) in item1.children" :key="item2.id" :class="[i2 === 0 ? '' : 'bdtop', 'vcenter']" vcenter>
            <el-col :span="12">
              <el-tag type="success" closable @close="removeRightById(item2.id)">{{item2.menuName}}</el-tag>
              <i class="el-icon-caret-right"></i>
            </el-col>
            <el-col :span="12">
              <el-tag
                type="warning"
                v-for="(item3) in item2.children"
                :key="item3.id"
                closable
                @close="removeRightById(item3.id)"
              >{{ item3.menuName}}</el-tag>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </template>
    <span slot="footer" class="dialog-footer">
      <el-button @click="delRightDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="delRightDialogVisible = false">确 定</el-button>
    </span>
  </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      // 所有角色列表
      rolesList: [],
      // 查询角色参数
      queryInfo: {
        page: 1,
        // 当前每页显示多少条数据
        pageSize: 5
      },
      // 分配权限框
      setRightDialogVisible: false,
      // 删除权限对话框
      delRightDialogVisible: false,
      // 权限列表
      rightsList: [],
      //  树形控件的属性绑定对象
      treeProps: {
        label: 'menuName',
        children: 'children'
      },
      //   默认选中节点ID值
      defKeys: [],
      //   添加用户对话框
      AddRoleDialogVisible: false,
      // 添加角色表单
      addRoleForm: {},
      //   添加角色表单验证
      addRoleFormRules: {
        roleCode: [
          { required: true, message: '请输入角色码', trigger: 'blur' }
        ],
        roleName: [
          { required: true, message: '请输入角色名称', trigger: 'blur' }
        ],
        roleDescription: [
          { required: true, message: '请输入角色描述', trigger: 'blur' }
        ]
      },
      //   编辑角色信息
      editRoleForm: {
        id: '',
        roleCode: '',
        roleName: '',
        roleDescription: ''
      },
      editRoleDialogVisible: false,
      editRoleFormRules: {
        roleCode: [
          { required: true, message: '请输入角色码', trigger: 'blur' }
        ],
        roleName: [
          { required: true, message: '请输入角色名称', trigger: 'blur' }
        ],
        roleDescription: [
          { required: true, message: '请输入角色描述', trigger: 'blur' }
        ]
      },
      //   当前即将分配权限的Id
      roleId: '',
      // 取消勾选的id
      cancelKeys: [],
      // 角色对应菜单信息
      roleMenu: []
    }
  },
  created () {
    this.getRolesList()
  },
  methods: {
    async getRolesList () {
      const { data: res } = await this.$http.get('role', {
        params: this.queryInfo
      })
      if (res.state !== 'success') {
        return this.$message.error('获取角色列表失败！')
      }
      this.rolesList = res.result
    },
    // 根据ID删除对应的权限
    async removeRightById (rightId) {
      // 弹框提示 删除
      const confirmResult = await this.$confirm(
        '此操作将永久删除该权限, 是否继续?',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).catch(err => err)
      // 点击确定 返回值为：confirm
      // 点击取消 返回值为： cancel
      if (confirmResult !== 'confirm') {
        this.$message.info('已取消权限删除')
      }
      var param = {'roleId': this.roleId, 'menuId': rightId}
      const { data: res } = await this.$http.delete(
        '/roleMenuDelete',
        {data: param}
      )
      if (res.state !== 'success') {
        return this.$message.error('删除权限失败！')
      }
      this.rolesList = res.result
      //   不建议使用
      this.getRolesList()
    },
    // 展示分配权限
    async showSetRightDialog (role) {
      this.roleId = role.id
      // // 获取角色的所有权限
      const { data: res1 } = await this.$http.get('/menuPageTreeAllByRole/' + role.id)
      const { data: res } = await this.$http.get('menuTreeAll')
      if (res.state !== 'success') {
        return this.$message.error('获取权限数据失败！')
      }
      // //   获取权限树
      this.rightsList = res.result
      // //   递归获取二级节点的id
      for (var i = 0; i < res1.result.length; i++) {
        this.getLeafkeys(res1.result[i], this.defKeys)
      }
      // this.getLeafkeys(res1.result, this.defKeys)
      this.setRightDialogVisible = true
    },
    // 展示删除权限
    async showDelRightDialog (role) {
      this.roleId = role.id
      const { data: res } = await this.$http.get('/roleMenuTree/' + role.id)
      this.roleMenu = res.result
      console.log(this.roleMenu)
      if (res.state !== 'success') {
        return this.$message.error('获取权限数据失败！')
      }
      this.delRightDialogVisible = true
    },
    // 通过递归 获取角色下二级权限的 id, 并保存到defKeys数组
    getLeafkeys (node, arr) {
      // 没有children属性，则是二级节点
      if (node.children === undefined || node.children.length === 0) {
        return arr.push(node.id)
      }
      node.children.forEach(item => this.getLeafkeys(item, arr))
    },
    // 权限对话框关闭事件
    setRightDialogClosed () {
      this.defKeys = []
    },
    // 点击为角色分配权限
    async allotRights () {
      const keys = [
        ...this.$refs.treeRef.getCheckedKeys(),
        ...this.$refs.treeRef.getHalfCheckedKeys()
      ]
      for (const key of keys) {
        const { data: res } = await this.$http.post('roleMenuAdd', {'roleId': this.roleId, 'menuId': key})
        if (res.state !== 'success') return this.$message.error('更新权限失败')
      }
      this.$message.success('更新权限成功')
      this.getRolesList()
      this.setRightDialogVisible = false
    },
    // 添加角色对话框的关闭
    addRoleDialogClosed () {
      this.$refs.addRoleFormRef.resetFields()
    },
    // 添加角色
    addRoles () {
      this.$refs.addRoleFormRef.validate(async valid => {
        if (!valid) return
        const { data: res } = await this.$http.post('role', this.addRoleForm)
        if (res.state !== 'success') {
          this.$message.error('添加角色失败！')
        }
        this.$message.success('添加角色成功！')
        this.AddRoleDialogVisible = false
        this.getRolesList()
      })
    },
    // 编辑角色
    async showEditDialog (id) {
      const { data: res } = await this.$http.get('/role?roleId=' + id)
      if (res.state !== 'success') {
        return this.$message.error('查询角色信息失败！')
      }
      this.editRoleForm.id = res.result.id
      this.editRoleForm.roleCode = res.result.roleCode
      this.editRoleForm.roleName = res.result.roleName
      this.editRoleForm.roleDescription = res.result.roleDescription
      this.editRoleDialogVisible = true
    },
    editRoles () {
      this.$refs.editRoleFormRef.validate(async valid => {
        // console.log(valid)
        // 表单预校验失败
        if (!valid) return
        const { data: res } = await this.$http.put('role', this.editRoleForm)
        if (res.state !== 'success') {
          this.$message.error('更新角色信息失败！')
        }
        // 隐藏编辑角色对话框
        this.editRoleDialogVisible = false
        this.$message.success('更新角色信息成功！')
        this.getRolesList()
      })
    },
    // 删除角色
    async removeRoleById (id) {
      const confirmResult = await this.$confirm(
        '此操作将永久删除该角色, 是否继续?',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).catch(err => err)
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除')
      }
      const { data: res } = await this.$http.delete('role?roleId=' + id)
      if (res.state !== 'success') return this.$message.error('删除角色失败！')
      this.$message.success('删除角色成功！')
      this.getRolesList()
    }
  }
}
</script>

<style lang="less" scoped>
.el-tag {
  margin: 7px;
}
.bdtop {
  border-top: 1px solid #eee;
}
.bdbottom {
  border-bottom: 1px solid #eee;
}
.vcenter {
  display: flex;
  align-items: center;
}
</style>
