<template>
  <div>
    <div class="info_div">
      <el-form label-position="left" label-width="80px">
        <el-form-item label="年龄：">
          <el-select
            v-model="newStandard.ageMin"
            placeholder="请选择"
            class="select"
          >
            <el-option key="不限" label="不限" :value="0"></el-option>
            <el-option
              v-for="count in 82"
              :key="count + 17"
              :label="count + 17"
              :value="count + 17"
            >
            </el-option>
          </el-select>
          <span>至</span>
          <el-select
            v-model="newStandard.ageMax"
            placeholder="请选择"
            class="select"
          >
            <el-option key="不限" label="不限" :value="999"></el-option>
            <el-option
              v-for="count in 82"
              :key="count + 17"
              :label="count + 17"
              :value="count + 17"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="身高：">
          <el-select
            v-model="newStandard.heightMin"
            placeholder="请选择"
            class="select"
          >
            <el-option key="不限" label="不限" :value="0"></el-option>
            <el-option
              v-for="count in 82"
              :key="count + 128"
              :label="count + 128"
              :value="count + 128"
            >
            </el-option>
          </el-select>
          <span>至</span>
          <el-select
            v-model="newStandard.heightMax"
            placeholder="请选择"
            class="select"
          >
            <el-option key="不限" label="不限" :value="999"></el-option>
            <el-option
              v-for="count in 82"
              :key="count + 128"
              :label="count + 128"
              :value="count + 128"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="月收入：">
          <el-select
            v-model="newStandard.salary"
            placeholder="请选择"
            class="single_select"
          >
            <el-option key="不限" label="不限" value="不限"></el-option>
            <el-option
              v-for="item in salaryList"
              :key="item"
              :label="item"
              :value="item"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学历:">
          <el-select
            v-model="newStandard.education"
            placeholder="请选择"
            class="single_select"
          >
            <el-option key="不限" label="不限" value="不限"></el-option>

            <el-option
              v-for="item in educationList"
              :key="item"
              :label="item"
              :value="item"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="住址:">
          <el-cascader
            ref="cityCascader"
            :options="cities"
            @change="addressChange"
            v-model="newAddressCode"
            class="single_select"
          ></el-cascader>
        </el-form-item>
        <el-form-item label="婚姻状况:">
          <el-radio v-model="newStandard.marriage" :label="0">未婚</el-radio>
          <el-radio v-model="newStandard.marriage" :label="1">离婚</el-radio>
          <el-radio v-model="newStandard.marriage" :label="2">丧偶</el-radio>
          <el-radio v-model="newStandard.marriage" :label="3">不限</el-radio>
        </el-form-item>
      </el-form>
      <el-alert
        title="这些选项会影响系统为您推荐的用户列表~~~"
        type="success"
        :closable="false"
      >
      </el-alert>
      <p></p>
      <el-button type="primary" @click="changeStandard">保存</el-button>
      <el-button type="primary" @click="redo">重置</el-button>
    </div>
  </div>
</template>

<script>
import { regionData } from "element-china-area-data";
import Axios from "axios";
export default {
  data() {
    return {
      standard: {
        ageMin: 0,
        ageMax: 999,
        heightMin: 0,
        heightMax: 999,
        salary: "不限",
        education: "不限",
        address: "江苏省/宿迁市/泗阳县",
        marriage: 3,
      },
      newStandard: {
        ageMin: 0,
        ageMax: 999,
        heightMin: 0,
        heightMax: 999,
        salary: "不限",
        education: "不限",
        address: "江苏省/宿迁市/泗阳县",
        marriage: 3,
      },
      cities: [],
      addressCode: [],
      newAddressCode: [],
      educationList: [],
      salaryList: [],
    };
  },
  methods: {
    addressChange() {
      let app = this;
      app.newStandard.address = "";
      let temp = app.$refs.cityCascader.getCheckedNodes()[0].pathLabels;
      for (let i = 0; i < temp.length; i++) {
        app.newStandard.address = app.newStandard.address + temp[i];
        if (i != temp.length - 1) {
          app.newStandard.address += "/";
        }
      }
      console.log(app.newStandard.address);
    },
    redo() {
      let app = this;
      app.newStandard = JSON.parse(JSON.stringify(app.standard));
      app.newAddressCode = JSON.parse(JSON.stringify(app.addressCode));
    },
    changeStandard() {
      let app = this;
      Axios.post("/userInfo/changeStandard", app.newStandard)
        .then(function (res) {
          if (res.data.result) {
            app.$message({
              message: "修改成功！",
              type: "success",
            });
          }
        })
        .catch(function (error) {});
    },
  },
  created() {
    let app = this;
    app.cities = JSON.parse(JSON.stringify(regionData));
    let tempAddress = { label: "不限", value: "不限" };
    app.cities.splice(0, 0, tempAddress);
    for (let i = 0; i < app.cities.length; i++) {
      if (app.cities[i].children != null) {
        app.cities[i].children.splice(0, 0, tempAddress);
        for (let j = 0; j < app.cities[i].children.length; j++) {
          if (app.cities[i].children[j].children != null) {
            app.cities[i].children[j].children.splice(0, 0, tempAddress);
          }
        }
      }
    }
    Axios.get("../../../static/infoList.json").then(function (res) {
      app.salaryList = res.data.salaryList;
      app.educationList = res.data.educationList;
    });
    Axios.get("/userInfo/getStandard")
      .then(function (res) {
        if (res.data.result) {
          console.log(res.data.message);
          app.standard = JSON.parse(JSON.stringify(res.data.message));
          app.newStandard = JSON.parse(JSON.stringify(res.data.message));
          let temp = app.cities;
          let cityArray = app.standard.address.split("/");
          for (let i = 0; i < cityArray.length; i++) {
            for (let j = 0; j < temp.length; j++) {
              if (cityArray[i] == temp[j].label) {
                app.addressCode.push(temp[j].value);
                temp = temp[j].children;
                break;
              }
            }
          }
          app.newAddressCode = JSON.parse(JSON.stringify(app.addressCode));
          console.log(app.newAddressCode);
        }
      })
      .catch(function (error) {});
    app.$emit("getIndex", "/personalInfo/dateStandard");
  },
};
</script>

<style scoped>
.info_div {
  margin-top: 30px;
  width: 500px;
  margin-left: 100px;
}

.select {
  width: 150px;
}

.single_select {
  width: 300px;
}
</style>
