<template>
  <el-dialog :title="$t('table.add')" width="560px" :visible.sync="visible" @close="onClose">
    <el-form :model="form" :rules="rules" ref="dialog-form" label-width="70px" size="small">
      <#list entityClass.columns as column>
      <#if !column.primaryKey>
      <el-form-item :label="$t('${entityClass.className ? uncap_first}.${column.fieldName}')" prop="${column.fieldName}">
        <el-input v-model="form.${column.fieldName}"></el-input>
      </el-form-item>
      </#if>
      </#list>
    </el-form>
    <div slot="footer" class="dialog-footer" v-loading="submitting" :element-loading-text="$t('dialog.submitting')" element-loading-spinner="el-icon-loading">
      <el-button @click="onClose">{{$t('dialog.cancel')}}</el-button>
      <el-button type="primary" @click="save">{{$t('dialog.save')}}</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import { vsprintf } from 'sprintf-js/dist/sprintf.min.js'
  import { saveToSubmit } from '@/utils/utils'

  export default {
    data() {
      var form = JSON.parse(JSON.stringify(this.$parent.selectedRow));
      return {
        visible: true,
        submitting: false,
        form: form,
        rules:{
          <#list entityClass.columns as column>
          <#if !column.primaryKey>
          ${column.fieldName}:[
            <#if column.nullable==1>
            { required: true, message:this.$t('rules.message.required'), trigger: 'blur' },
            </#if>
            <#if column.maxLength gt 0 >
            { max: ${column.maxLength}, message: vsprintf(this.$t('rules.message.maxLen'), ${column.maxLength}), trigger: 'blur' }
            </#if>
           ],
          </#if>
          </#list>
        }
      };
    },
    methods: {
      save(){
        updateToSubmit.apply(this, [this.form])
      },
      onClose(){
        this.$router.push("/");
      }
    }
  };
</script>
