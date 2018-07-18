<template>
    <div class="app-container">
        <el-form :model="query" :inline="true" ref="searchForm">
            <#list entityClass.columns as column>
            <#if !column.primaryKey>
            <el-form-item prop="${column.fieldName}">
                <el-input v-model="query.${column.fieldName}" :placeholder="$t('${entityClass.className ? uncap_first}.${column.fieldName}')" clearable maxlength="30"></el-input>
            </el-form-item>
            </#if>
            </#list>
        </el-form>
        <el-row style="margin-bottom: 10px;">
            <el-button-group>
                <el-button type="primary" size="small" icon="el-icon-plus" @click="toAdd">{{$t('table.add')}}</el-button>
                <el-button type="primary" size="small" icon="el-icon-edit" @click="toUpdate">{{$t('table.update')}}</el-button>
                <el-button type="primary" size="small" icon="el-icon-delete" @click="toDelete">{{$t('table.delete')}}</el-button>
                <el-button type="primary" size="small" icon="el-icon-document" @click="toDetail">{{$t('table.detail')}}</el-button>
            </el-button-group>
            <el-button-group style="position: absolute; right: 0px;">
                <el-button type="primary" size="small" icon="el-icon-search" @click="reload">{{$t('table.search')}}</el-button>
                <el-button type="default" size="small" icon="el-icon-refresh" @click="resetForm">{{$t('table.reset')}}</el-button>
            </el-button-group>
        </el-row>
        <el-table v-loading="loading" :data="tableData" border highlight-current-row @row-click="onRowSelected" style="width: 100%" @sort-change="sortChange">
            <el-table-column type="index" :label="$t('table.index')" :index="indexMethod" align="center" width="55"></el-table-column>
            <#list entityClass.columns as column>
            <el-table-column prop="${column.fieldName}" :label="$t('${entityClass.className ? uncap_first}.${column.fieldName}')"></el-table-column>
            </#list>
        </el-table>
        <div class="pagination-container">
            <el-pagination
                    background
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page.sync="currentPage"
                    :page-sizes="pageSizes"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>
        <transition name="fade">
            <router-view/>
        </transition>
    </div>
</template>

<script>
    import Vue from 'vue'
    import VueRouter from 'vue-router'
    import { fetchPage, fetchDelete } from '@/api/restful'
    import { tableOptions, tableMethods, loadTableData, reloadTableData, updateTableRow, deleteTableRow } from '@/utils/utils'
    export default {
        data() {
            return {
                ...tableOptions.apply(this),
                baseUrl: '/${entityClass.className ? uncap_first}',
                query: {
                    sort: 'id',
                    order: 'desc'
                }
            };
        },
        methods: {
            ...tableMethods.apply(this)
        },
        mounted: function () {
            this.reload();
        },
        router: new VueRouter({
            mode: 'abstract',
            routes: [
                { path: '/add', component: () => import('./dialog/add') },
                { path: '/update', component: () => import('./dialog/update') },
                { path: '/detail', component: () => import('./dialog/detail') }
            ]
        })
    }
</script>
