<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>添加商品</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card>
      <el-alert title="添加商品信息" type="info" center show-icon :closable="false"></el-alert>
      <el-steps :space="200" :active="activeIndex - 0" finish-status="success" align-center>
        <el-step title="基本信息"></el-step>
        <el-step title="商品参数"></el-step>
        <el-step title="商品属性"></el-step>
        <el-step title="商品图片"></el-step>
        <el-step title="商品内容"></el-step>
        <el-step title="完成"></el-step>
      </el-steps>
      <el-form
        :model="addForm"
        :rules="addFormRules"
        ref="addFormRef"
        label-width="100px"
        label-position="top"
      >
        <el-tabs
          v-model="activeIndex"
          :tab-position="'left'"
          :before-leave="beforeTabLeave"
          @tab-click="tabClicked"
        >
          <el-tab-pane label="基本信息" name="0">
            <el-form-item label="商品名称" prop="goods_name">
              <el-input v-model="addForm.goods_name"></el-input>
            </el-form-item>
            <el-form-item label="商品价格" prop="goods_price">
              <el-input
                v-model="addForm.goods_price"
                oninput="value=value.replace(/[^\d]/g,'')"
                type="number"
              ></el-input>
            </el-form-item>
            <el-form-item label="商品重量" prop="goods_weight">
              <el-input
                v-model="addForm.goods_weight"
                oninput="value=value.replace(/[^\d]/g,'')"
                type="number"
              ></el-input>
            </el-form-item>
            <el-form-item label="商品数量" prop="goods_number">
              <el-input
                v-model="addForm.goods_number"
                oninput="value=value.replace(/[^\d]/g,'')"
                type="number"
              ></el-input>
            </el-form-item>
            <el-form-item label="商品分类" prop="goods_cat">
              <el-alert title="注意：只允许选择第三级分类！" type="warning" :closable="false" show-icon></el-alert>
              <el-cascader
                v-model="addForm.goods_cat"
                :options="cateList"
                @change="handleChange"
                :props="{ expandTrigger: 'hover', value:'cat_id', label:'cat_name', children:'children',checkStrictly:true}"
                clearable
              ></el-cascader>
            </el-form-item>
          </el-tab-pane>
          <el-tab-pane label="商品参数" name="1">
            <el-form-item :label="item.attr_name" v-for="item in manyTableData" :key="item.attr_id">
              <el-checkbox-group v-model="item.attr_vals">
                <el-checkbox :label="cb" v-for="(cb, i) in item.attr_vals" :key="i" border></el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-tab-pane>
          <el-tab-pane label="商品属性" name="2">
            <el-form-item :label="item.attr_name" v-for="item in onlyTableData" :key="item.attr_id">
              <el-input v-model="item.attr_vals"></el-input>
            </el-form-item>
          </el-tab-pane>
          <el-tab-pane label="商品图片" name="3">
            <el-upload
              :action="uploadURL"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              list-type="picture"
              :headers="headerObj"
              :on-success="handleSuccess"
            >
              <el-button size="small" type="primary">选取图片</el-button>
            </el-upload>
          </el-tab-pane>
          <el-tab-pane label="商品内容" name="4">
            <!-- 富文本编辑器 -->
            <quill-editor v-model="addForm.goods_introduce"></quill-editor>
            <el-button type="primary" class="btnAdd" @click="add">添加商品</el-button>
          </el-tab-pane>
        </el-tabs>
      </el-form>
    </el-card>

    <el-dialog
  title="图片预览"
  :visible.sync="previewVisible"
  width="50%">
  <img :src="previewPath" alt="" class="previewImg">
</el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      activeIndex: "0",
      addForm: {
        goods_name: "",
        goods_price: 0,
        goods_weight: 0,
        goods_number: 0,
        goods_cat: [],
        pics:[],
        goods_introduce:'',
        attrs:[]
      },
      addFormRules: {
        goods_name: [
          {
            required: true,
            message: "请输入商品名称",
            trigger: "blur"
          }
        ],
        goods_price: [
          {
            required: true,
            message: "请输入商品价格",
            trigger: "blur"
          }
        ],
        goods_weight: [
          {
            required: true,
            message: "请输入商品重量",
            trigger: "blur"
          }
        ],
        goods_number: [
          {
            required: true,
            message: "请输入商品数量",
            trigger: "blur"
          }
        ],
        goods_cat: [
          {
            required: true,
            message: "请输入选择商品分类",
            trigger: "blur"
          }
        ]
      },
      cateList: [],
      manyTableData: [],
      onlyTableData: [],
      uploadURL: "http://localhost:8081/shop/fileUpload",
      headerObj: {
        Authorization: window.sessionStorage.getItem("token")
      },
      previewPath:'',
      previewVisible: false,
      id:this.$route.params.id
    };
  },
  created() {
    console.log(this.id)
    this.getCateList();
  },
  methods: {
    getCateList() {
      this.$http({
        method: "GET",
        url: "goods/getAllCategories"
      })
        .then(resp => {
          if (resp.data.code !== 200) {
            return this.$message.error("获取商品分类失败！");
          }
          this.cateList = resp.data.data;
          console.log(this.cateList);
        })
        .catch(error => {
          console.log(error);
        });
    },
    handleChange() {
      if (this.addForm.goods_cat.length !== 3) {
        this.addForm.goods_cat = [];
      }
      console.log(this.addForm.goods_cat);
    },
    beforeTabLeave(activeName, oldActiveName) {
      // console.log('即将离开的标签页'+oldActiveName)
      // console.log('即将进入的标签页'+activeName)
      // return false
      if (oldActiveName === "0" && this.addForm.goods_cat.length !== 3) {
        this.$message.error("请先选择商品分类！");
        return false;
      }
    },
    tabClicked() {
      //  console.log(this.activeIndex)
      //证明访问的是动态参数面板
      if (this.activeIndex === "1") {
        this.$http({
          method: "GET",
          url: "goods/attributes?id=" + this.cateId + "&sel=many"
        })
          .then(resp => {
            if (resp.data.code !== 200) {
              return this.$message.error("获取动态参数列表失败！");
            }
            console.log(resp.data.data);
            resp.data.data.forEach(item => {
              item.attr_vals =
                item.attr_vals.length === 0 ? [] : item.attr_vals.split(" ");
            });
            this.manyTableData = resp.data.data;
          })
          .catch(error => {
            console.log(error);
          });
      } else if (this.activeIndex === "2") {
        this.$http({
          method: "GET",
          url: "goods/attributes?id=" + this.cateId + "&sel=only"
        })
          .then(resp => {
            if (resp.data.code !== 200) {
              return this.$message.error("获取静态属性失败！");
            }
            console.log(resp.data.data);
            this.onlyTableData = resp.data.data;
          })
          .catch(error => {
            console.log(error);
          });
      }
    },
    //处理图片预览效果
    handlePreview(file) {
      console.log(file)
      this.previewPath = file.response.data.url
      this.previewVisible = true
    },
    //移除图片
    handleRemove(file) {
      console.log(file)
      //获取将要删除的图片的临时路径
      const filePath = file.response.data.tmp_path
      //从pics数组中，找到这个图片对应的索引值
      const i = this.addForm.pics.findIndex(x => 
      x.pic === filePath)
      //从数组中移除
      this.addForm.pics.splice(i,1)
      console.log(this.addForm)
    },
    //监听图片上传成功
    handleSuccess(response){
      console.log(response)
      const picInfo = {pic:response.data.tmp_path}
      this.addForm.pics.push(picInfo)
      console.log(this.addForm)
    },
    add() {
      console.log(this.addForm)
      this.$refs.addFormRef.validate(valid => {
        if(!valid) {
          return this.$message.error('请填写必要的表单项！')
        }
        //使用JSON对象的parse和stringify来进行深拷贝
        var form =  JSON.parse(JSON.stringify(this.addForm))
        form.goods_cat = form.goods_cat.join(',')
        //处理动态参数
        this.manyTableData.forEach(item => {
          const newInfo = {
            attr_id:item.attr_id,
            attr_value:item.attr_vals.join(' ')
          }
          this.addForm.attrs.push(newInfo)
        })
        //处理静态属性
        this.onlyTableData.forEach(item => {
          const newInfo = {
            attr_id:item.attr_id,
            attr_value:item.attr_vals
          }
          this.addForm.attrs.push(newInfo)
        })
        form.attrs = this.addForm.attrs
        console.log(form)

        this.$http({
          method: "POST",
          url: "goods/add",
          data:{
            goods_name: form.goods_name,
            goods_price: form.goods_price,
            goods_number: form.goods_number,
            goods_weight: form.goods_weight,
            cat_id: form.goods_cat,
            goods_introduce: form.goods_introduce,
            pics: form.pics,
            attrs: form.attrs
          }
        })
          .then(resp => {
            if (resp.data.code !== 200) {
              return this.$message.error("添加商品失败！");
            }
            this.$message.success("添加商品成功！")
            this.$router.push('/list')
          })
          .catch(error => {
            console.log(error);
          });
      })
    }
  },
  computed: {
    cateId() {
      if (this.addForm.goods_cat.length === 3) {
        return this.addForm.goods_cat[2];
      }
      return null;
    }
  }
};
</script>

<style lang="less" scoped>
.el-checkbox {
  margin: 0 10px 0 0 !important;
}
.previewImg{
  width: 100%;
}
.btnAdd{
  margin-top: 15px;
}
</style>