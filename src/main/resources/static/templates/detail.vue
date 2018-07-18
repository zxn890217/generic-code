<template>
  <el-dialog :title="$t('table.add')" width="560px" :visible.sync="visible" @close="onClose">
    <el-form :model="form" :rules="rules" ref="dialog-form" label-width="70px" size="small">
      <#list entityClass.columns as column>
      <#if !column.primaryKey>
      <el-form-item :label="$t('${entityClass.className ? uncap_first}.${column.fieldName}')">
        <el-input v-model="form.${column.fieldName}" readonly></el-input>
      </el-form-item>
      </#if>
      </#list>
    </el-form>
  </el-dialog>
</template>

<script>
  import { vsprintf } from 'sprintf-js/dist/sprintf.min.js'

  export default {
    data() {
      var form = JSON.parse(JSON.stringify(this.$parent.selectedRow));
      return {
        visible: true,
        form: form
      };
    },
    methods: {
      onClose(){
        this.$router.push("/");
      }
    }
  };
</script>
