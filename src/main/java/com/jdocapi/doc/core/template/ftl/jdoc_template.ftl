<!DOCTYPE html>
<html lang="zh-CN">
<#macro bpTree children>
    <#if children?? && children?size gt 0>
        <#list children as child>
            <#if child.subApiResponseParam?? && child.subApiResponseParam?size gt 0>
            <tr>
                <td>${child.name}</td>
                <td>${child.type}</td>
                <td>${child.required}</td>
                <td>${child.desc}]</td>
            </tr>
                        <tr>
                            <td></td>
                            <td colspan="3">
                                <table class="table">
                                    <tr>
                                        <th>参数名</th>
                                        <th>参数类型</th>
                                        <th>是否必填</th>
                                        <th>描述</th>
                                    </tr>
                                    <@bpTree children=child.respParams />
                                </table>
                            </td>
                        </tr>
            <#else>
            <tr>
                <td>${child.name}</td>
                <td>${child.type}</td>
                <td>${child.required}</td>
                <td>${child.desc}]</td>
            </tr>
            </#if>
        </#list>

    </#if>
</#macro>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, Chrome=1">
    <meta name="renderer" content="webkit">
    <title>JDoc</title>
    <style>
        body, div, dl, dt, dd, ul, ol, li, h1, h2, h3, h4, h5, h6, pre, form,
        fieldset, input, textarea, p, blockquote, th, td {
            margin: 0;
            padding: 0;
        }

        body {
            color: #666;
            font-size: 14px;
            background-color: #e4e5e6;
            font-family: 微软雅黑, Arial, 宋体;
        }

        ol, ul {
            list-style: none;
        }

        :focus {
            outline: 0;
        }

        a {
            text-decoration: none;
            transition: all 0.5s;
        }

        img {
            border: 0;
        }

        * {
            box-sizing: border-box;
        }

        .container-fluid {
            margin: auto;
        }

        .container-fluid:after {
            content: " ";
            display: table;
            clear: both;
        }

        h2 {
            font-size: 30px;
            line-height: 1.1;
        }

        h4 {
            font-size: 18px;
            line-height: 1.1;
            font-weight: 500;
        }

        header {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 56px;
            z-index: 99999;
            background-color: #fff;
            border-bottom: 1px solid #d1d4d7;
        }

        header .logo {
            float: left;
            width: 200px;
            height: 55px;
            background-position: center;
            background-repeat: no-repeat;
            background-image: url(data:image/png;base64,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);
        }

        header .headbtn {
            display: inline-block;
            width: 50px;
            height: 55px;
            line-height: 55px;
            color: #d1d4d7;
            font-size: 18px;
            text-align: center;
            background-position: center;
            background-repeat: no-repeat;
        }

        header .headbtn:hover {
            color: #1ab394;
        }

        header .sidebar-toggler {
            float: left;
            border-right: 1px solid #d1d4d7;
            background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABIAAAASCAIAAADZrBkAAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAKJJREFUeNrEkbEOwjAMRJ3WoRBRAUslRAYE//9BDEhUDCxQ0aokJE0JKV/gdCi3ePHTnc/Mew/xSmCUEF63U9lY8n6+O+5XAXOuiwjaWQUwYHOxQKV6GsW4WP7mxJWYx/lyp4fcyIPMA/ZW2pGDeqtagAFD5IzRHzAT/6ikr69lpamWfC1lkQWsbWptPlQb86yg2IaQmKQxR6XZ9JWMxL4CDABtRT//KgzIUAAAAABJRU5ErkJggg==);
        }

        header .sidebar-toggler:hover {
            background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABIAAAASCAIAAADZrBkAAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAKtJREFUeNpi/P//PwPpgImBLMDC8PFi2/FT54hVz5diGu4hDNT25/2dP7+OE6vtjcN7BgaQNm45B87bn34Tp4tZxEgURDPSOUh+3Ft0dO8moh1ZZBZoxQ/U9vXRge9EB8mfZ+deM4C1sQiqsLB9IjoCVAQHIkj+vjh25vTVf0SqF7AytNXmAGr7cGPO60c7ibXmUeUTW20VoCNZeMVIcB2XGA/9g4RMbQABBgAD7j4iI2d4zwAAAABJRU5ErkJggg==);
        }

        header .searchbox {
            float: left;
            width: calc(100% - 330px);
            padding-left: 30px;
        }

        .form-control {
            border: 1px solid #e5e6e7;
            border-radius: 1px;
            color: inherit;
            display: block;
            width: 100%;
            height: 34px;
            padding: 6px 12px;
            font-size: 14px;
            line-height: 1.4286;
            background-color: #fff;
        }

        .form-control:focus {
            border-color: #1ab394;
        }

        header .form-control {
            border: 0;
            height: 55px;
        }

        header .searchbtn {
            float: right;
            padding-right: 30px;
        }

        header .searchbtn a {
            background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABIAAAASCAIAAADZrBkAAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAX1JREFUeNqMk9tugkAQhlmORUAIaVUMaO2FSd//cRpprQe0clBxVVhgSzkUSmnjXJF/95t/mJkFGGOiGfH1HESAFXma+CPqB/HJ/djY/hklpQJotqP0tKHc5EHpdrHMxe6StKUGTHcwNWTqFxasZ2/2tag2dRAElsLoDC/X0pnp6s+G+KPIeGc5OQM4dTzShSovchcvFozSj+N27otjqdDJ1Mra5xAjG5M68yWpxljlQJbBt71vnSRiD4aZEy/pUsufCdqDlHUkCeGhwk4I5eUyItXebZHNGxkhWGFlYBwTNwdJyCyb5wrgtf2OdwmLeoSam9LJufC4cloM4cqF2RRoXpFrGKf3hKzWBG7M2e5cQ6P9yjT3KJ+N0hcb447dubk8lfdJhmNJQOAoCKNqYcGdOppqfGO5Iud9vvYR/q8RFQnqLyCG3nrrHtKNyjVAcUK3P+wz9qvpBrhGgraH0xLQqpOP5I2DErTJU7FlOELhrW6Fp7O0jvS9PvgUYACObr6PmUBJrgAAAABJRU5ErkJggg==);
        }

        .navbar-side {
            position: fixed;
            top: 56px;
            left: 0;
            z-index: 9999;
            width: 250px;
            height: calc(100vh - 56px);
            color: #fff;
            overflow: auto;
            background-color: #2f4050;
            border-radius: 0;
            transition: all 0.5s;
        }

        .nav > li > a {
            display: block;
            color: #a7b1c2;
            font-weight: 600;
            padding: 14px 20px 14px 25px;
        }

        .nav > li > a:hover, .nav > li > a:focus {
            background-color: #293846;
            color: white;
        }

        .arrow {
            float: right;
            margin-top: 2px;
            width: 14px;
            height: 14px;
            transition: all 0.5s;
            background-position: center;
            background-repeat: no-repeat;
            background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABIAAAASCAYAAABWzo5XAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAKpJREFUeNpi/P//PwM1ABMDlQDdDeIG4iVAfB+Iu8k1iA2IZwBxNBD/A+JvWFWBAhsP5gLihf8h4AoQa+JSi88QNiBeBDXkOj5D8BkEcskCYlxCyKC5UEPuALESIUNAGFdgX4LSokCsS1S84rElHeqqb0AcQq7XkA37AzUsnBKDkF32FYhDgViAXINghv2FumwRNjUsRGaRmUAsAMS2QHwYmwLG4VuMAAQYAA9A0dvaO7nIAAAAAElFTkSuQmCC);
        }

        .titleicon {
            display: inline-block;
            width: 16px;
            height: 14px;
            background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAOCAYAAAAmL5yKAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAEhJREFUeNpi/P//PwMlgOXg0dMgExjJNYCJDD3/oZigASgKqekCsgzA6Rqqu4Aofw9IGNDOABY0PiOJbLgBZGcIRkozE0CAAQCXRBJn+pYeiAAAAABJRU5ErkJggg==);
        }

        .home .titleicon {
            background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAOCAYAAAAmL5yKAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAFpJREFUeNpi/P//P8Nvhl8MOMAnKM2HTZKVgY2BiQE3+ISDjQKYiNCM1xAmYhXikmMiQTNWNUwkasZQy0SGZhRDWNCiiViD+AjFAtGAhRhbSI1GksAwMAAgwAD2iBOD7Bkf1gAAAABJRU5ErkJggg==);
        }

        .nav > li.active {
            border-left: 4px solid #19aa8d;
            background: #293846;
        }

        .nav > li.active .arrow {
            transform: rotate(-90deg);
        }

        .nav-second-level {
            display: none;
        }

        .nav-second-level li a {
            padding: 7px 15px 7px 52px;
        }

        .page-wrapper {
            padding-top: 71px;
            padding-left: 265px;
            padding-right: 15px;
            padding-bottom: 15px;
            transition: all 0.5s;
        }

        .pageitem {
            display: none;
            transition: all 0.5s;
        }

        .page-wrapper .pagewelcom {
            transition: all 0.5s;
            min-height: calc(100vh - 86px);
            padding-bottom: 40px;
        }

        .welcomfooter {
            position: absolute;
            bottom: 0;
            left: 0;
            width: 100%;
            padding: 10px;
            text-align: center;
            height: 40px;
            border-top: solid 1px #eee;
        }

        .welcomfooter span {
            padding: 0 15px;
        }

        .pageitem .commet, .pageitem .return, .pagewelcom {
            padding: 10px;
            background-color: #fff;
            position: relative;
        }

        .page-wrapper h2 {
            padding: 10px;
            color: #333;
            font-weight: 600;
        }

        .pageitem .commet > p {
            padding: 10px;
        }

        .table {
            width: 100%;
            max-width: 100%;
            margin-bottom: 20px;
            border-spacing: 0;
            border-collapse: collapse;
            background-color: transparent;
        }

        .table th, .table td {
            padding: 8px;
            text-align: left;
            line-height: 1.42857143;
            vertical-align: top;
            border-top: 1px solid #ddd;
        }

        .reception .table {
            border-bottom: solid 1px #ddd;
        }

        .pageitem .form {
            padding: 15px;
            padding-right: 45px;
        }

        .form-group {
            overflow: hidden;
            margin-bottom: 15px;
        }

        .control-label {
            float: left;
            width: 25%;
            padding-top: 7px;
            margin-bottom: 0;
            text-align: right;
            font-weight: 700;
            padding-right: 15px;
        }

        .control-key {
            float: left;
            width: 25%;
            padding-right: 15px;
        }

        .control-key .form-control {
            text-align: right;
            width: 130px;
            float: right;
        }

        .inputbox .close {
            position: absolute;
            width: 32px;
            height: 32px;
            border-left: solid 1px #e5e6e7;
            top: 1px;
            right: 16px;
            text-align: center;
            line-height: 34px;
            color: #f30;
            background-color: #fff;
        }

        .inputbox .close:hover {
            color: #fff;
            background-color: #f30;
        }

        .inputbox {
            margin-left: 25%;
            padding-left: 15px;
            padding-right: 15px;
            position: relative;
        }

        .radio-inline {
            position: relative;
            display: inline-block;
            padding-left: 20px;
            padding-top: 7px;
            font-weight: 400;
            vertical-align: middle;
            font-weight: 700;
        }

        .radio-inline label {
            cursor: pointer;
        }

        .btn {
            display: inline-block;
            padding: 6px 12px;
            margin-bottom: 0;
            font-size: 14px;
            font-weight: 400;
            line-height: 1.42857143;
            text-align: center;
            white-space: nowrap;
            vertical-align: middle;
            cursor: pointer;
            background-color: #ddd;
            border: 1px solid transparent;
            border-radius: 4px;
        }

        .btn:hover {
            color: #333;
            background-color: #ccc;
        }

        .btn-primary {
            color: #fff;
            background-color: #337ab7;
            border-color: #2e6da4;
        }

        .btn-primary:hover {
            color: #fff;
            background-color: #286090;
            border-color: #204d74;
        }

        .form-content {
            padding: 10px;
        }

        .sidebarhidden .navbar {
            margin-left: -250px;
        }

        .sidebarhidden .page-wrapper {
            padding-left: 15px;
        }

        .codepanel {
            padding: 15px;
            color: #333;
            background-color: #f6f6f6;
            border-radius: 3px;
        }

        .codespaces {
            display: inline-block;
            width: 20px;
            height: 16px;
            vertical-align: middle;
        }

        .codekey {
            color: #8757bd;
        }

        .codeval {
            color: #ea854e;
        }

        .urlchart-name {
            padding-top: 20px;
            padding-left: 40px;
            padding-bottom: 10px;
            font-weight: bold;
        }

        .urlchart {
            margin: 0 30px;
            padding: 10px;
            background-color: #f3f3f3;
        }

        .urlchart-timebar {
            margin-left: 200px;
            border-bottom: solid 1px #ccc;
            overflow: hidden;
            padding-right: 50px;
        }

        .urlchart-timebar span {
            float: left;
            width: 25%;
            text-align: right;
        }

        .urlchart-item {
            overflow: hidden;
            padding-right: 50px;
            line-height: 20px;
        }

        .urlchart-title {
            float: left;
            text-align: right;
            width: 200px;
            padding: 10px;
            border-right: solid 1px #ccc;
        }

        .urlchart-title span {
            color: #00c853;
        }

        .urlchart-bar {
            margin-left: 200px;
        }

        .urlchart-bar:after {
            content: "";
            display: table;
            clear: both;
        }

        .urlchart-bar span {
            margin: 10px 0;
            background-color: #C23531;
            float: left;
            height: 20px;
        }

        .urlchart-time {
            float: left;
            margin-top: 10px;
        }
    </style>
</head>

<body>
<div class="container-fluid">
    <header>
        <a href="#" class="logo"></a>
        <a href="javascript:" class="headbtn sidebar-toggler" onclick="sidebarToggle();"></a>
        <div class="searchbox">
            <input type="text" placeholder="请输入搜索内容..." autocomplete="off" class="form-control" id="searchinput">
        </div>
        <div class="searchbtn">
            <a href="javascript:" class="headbtn"></a>
        </div>
    </header>
    <div id="wrapper">
    </div>
</div>

<!-- 模板 -->
<script id="sidenav-temp" type="text/html">
    <nav class="navbar navbar-side">
        <ul class="nav">
            <li>
                <a href="javascript:" class="home">
                    <i class="titleicon"></i>
                    <span class="nav-label">首页</span>
                </a>
            </li>
            {{each}}
            <li>
                <a href="javascript:">
                    <i class="titleicon"></i>
                    <span class="nav-label">{{$value.title}}</span>
                    <span class="arrow"></span>
                </a>
                {{if $value.apiActions}}
                <ul class="nav nav-second-level">
                    {{each $value.apiActions}}
                    <li>
                        <a href="javascript:" class="listitem">{{$value.title}}</a>
                    </li>
                    {{/each}}
                </ul>
                {{/if}}
            </li>
            {{/each}}
        </ul>
    </nav>
    <div class="page-wrapper row">
        <div class="pagewelcom">
            <h2>欢迎使用JDoc</h2>
            {{each}}
            <h4 class="urlchart-name">{{$value.title}}</h4>
            <div class="urlchart">
                {{#$value.apiActions | urlcharts}}
            </div>
            {{/each}}
            <div class="welcomfooter">
                <span>前端模板作者：周星华（QQ：499244819）</span>
                <span>后端模板作者：梁豪（QQ：451671299）</span>
                <span>QQ交流群：232558246</span>
            </div>
        </div>
        {{each}}
        {{each $value.apiActions}}
        <div class="pageitem">
            <div class="commet">
                <h2>{{$value.title}}</h2>
                <p>{{$value.desc}}</p>
                <p>方法签名: {{$value.methodSign}}</p>
                <p>接口地址: {{$value.url}}</p>
                <p>请求类型: {{$value.requestType}}</p>
                <h2>入参</h2>
                <div class="reception">
                    <table class="table">
                        <tr>
                            <th>参数名</th>
                            <th>参数类型</th>
                            <th>是否必填</th>
                            <th>描述</th>
                        </tr>
                        {{each $value.reqParams}}
                        {{if $value.subApiRequestParam}}
                        <tr>
                            <td>{{$value.name}}</td>
                            <td>{{$value.type}}</td>
                            <td>{{$value.required}}</td>
                            <td>{{$value.desc}}</td>
                        </tr>
                        <tr>
                            <td></td>
                            <td colspan="3">
                                <table class="table">
                                    <tr>
                                        <th>参数名</th>
                                        <th>参数类型</th>
                                        <th>是否必填</th>
                                        <th>描述</th>
                                    </tr>
                                    {{each $value.subApiRequestParam}}
                                    {{if $value.subApiRequestParam}}
                                    <tr>
                                        <td>{{$value.name}}</td>
                                        <td>{{$value.type}}</td>
                                        <td>{{$value.required}}</td>
                                        <td>{{$value.desc}}</td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td colspan="3">
                                            <table class="table">
                                                <tr>
                                                    <th>参数名</th>
                                                    <th>参数类型</th>
                                                    <th>是否必填</th>
                                                    <th>描述</th>
                                                </tr>
                                                {{each $value.subApiRequestParam}}
                                                <tr>
                                                    <td>{{$value.name}}</td>
                                                    <td>{{$value.type}}</td>
                                                    <td>{{$value.required}}</td>
                                                    <td>{{$value.desc}}</td>
                                                </tr>
                                                {{/each}}
                                            </table>
                                        </td>
                                    </tr>
                                    {{else}}
                                    <tr>
                                        <td>{{$value.name}}</td>
                                        <td>{{$value.type}}</td>
                                        <td>{{$value.required}}</td>
                                        <td>{{$value.desc}}</td>
                                    </tr>
                                    {{/if}}
                                    {{/each}}
                                </table>
                            </td>
                        </tr>
                        {{else}}
                        <tr>
                            <td>{{$value.name}}</td>
                            <td>{{$value.type}}</td>
                            <td>{{$value.required}}</td>
                            <td>{{$value.desc}}</td>
                        </tr>
                        {{/if}}
                        {{/each}}
                    </table>
                </div>
                <h2>出参</h2>
                <p>描述:{{$value.returnDesc}}</p>
                <div class="reception">
                    <table class="table">
                        <tr>
                            <th>参数名</th>
                            <th>参数类型</th>
                            <th>是否必填</th>
                            <th>描述</th>
                        </tr>
                        {{each $value.respParams}}
                        {{if $value.subApiResponseParam}}
                        <tr>
                            <td>{{$value.name}}</td>
                            <td>{{$value.type}}</td>
                            <td>{{$value.required}}</td>
                            <td>{{$value.desc}}</td>
                        </tr>
                        <tr>
                            <td></td>
                            <td colspan="3">
                                <table class="table">
                                    <tr>
                                        <th>参数名</th>
                                        <th>参数类型</th>
                                        <th>是否必填</th>
                                        <th>描述</th>
                                    </tr>
                                    {{each $value.subApiResponseParam}}
                                    {{if $value.subApiResponseParam}}
                                    <tr>
                                        <td>{{$value.name}}</td>
                                        <td>{{$value.type}}</td>
                                        <td>{{$value.required}}</td>
                                        <td>{{$value.desc}}</td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td colspan="3">
                                            <table class="table">
                                                <tr>
                                                    <th>参数名</th>
                                                    <th>参数类型</th>
                                                    <th>是否必填</th>
                                                    <th>描述</th>
                                                </tr>
                                                {{each $value.subApiResponseParam}}
                                                <tr>
                                                    <td>{{$value.name}}</td>
                                                    <td>{{$value.type}}</td>
                                                    <td>{{$value.required}}</td>
                                                    <td>{{$value.desc}}</td>
                                                </tr>
                                                {{/each}}
                                            </table>
                                        </td>
                                    </tr>
                                    {{else}}
                                    <tr>
                                        <td>{{$value.name}}</td>
                                        <td>{{$value.type}}</td>
                                        <td>{{$value.required}}</td>
                                        <td>{{$value.desc}}</td>
                                    </tr>
                                    {{/if}}
                                    {{/each}}
                                </table>
                            </td>
                        </tr>
                        {{else}}
                        <tr>
                            <td>{{$value.name}}</td>
                            <td>{{$value.type}}</td>
                            <td>{{$value.required}}</td>
                            <td>{{$value.desc}}</td>
                        </tr>
                        {{/if}}
                        {{/each}}
                    </table>
                </div>
                <p>返回示例: {{#$value.respText | formatjson}}</p>
            </div>
            <div class="return">
                <h2>测试</h2>
                <div class="form">
                    {{each $value.reqParams}}
                    <div class="form-group">
                        <label class="control-label">{{$value.desc}}</label>
                        <div class="inputbox">
                            <input type="text" name="{{$value.name}}" class="form-control">
                        </div>
                    </div>
                    {{/each}}
                    <div class="form-group">
                        <label class="control-label">请求方式</label>
                        <div class="inputbox">
                            <div class="radio-inline">
                                <label>
                                    <input type="radio" checked="checked" value="post" name="sendType-{{$value.url}}">
                                    post
                                </label>
                            </div>
                            <div class="radio-inline">
                                <label>
                                    <input type="radio" value="get" name="sendType-{{$value.url}}"> get
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="userdefined"></div>
                    <div class="form-group">
                        <div class="form-btn inputbox">
                            <button class="btn btn-primary" data-url="{{$value.url}}" onclick="testSend(this);">测试
                            </button>
                            <button class="btn" onclick="reset(this);">重置</button>
                            <button class="btn" onclick="userDefined(this);">自定义参数</button>
                        </div>
                    </div>
                </div>
                <h2>返回内容</h2>
                <div class="form-content">

                </div>
            </div>
        </div>
        {{/each}} {{/each}}
    </div>
</script>

<!-- 全局js -->
<script src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script>
    /*!art-template - Template Engine | http://aui.github.com/artTemplate/*/
    !function () {
        function a(a) {
            return a.replace(t, "").replace(u, ",").replace(v, "").replace(w, "").replace(x, "").split(y)
        }

        function b(a) {
            return "'" + a.replace(/('|\\)/g, "\\$1").replace(/\r/g, "\\r").replace(/\n/g, "\\n") + "'"
        }

        function c(c, d) {
            function e(a) {
                return m += a.split(/\n/).length - 1, k && (a = a.replace(/\s+/g, " ").replace( / <!--[\w\W]*?-->/g, "")), a && (a = s[1] + b(a) + s[2] + "\n"), a
            }

            function f(b) {
                var c = m;
                if (j ? b = j(b, d) : g && (b = b.replace(/\n/g, function () {
                    return m++, "$line=" + m + ";"
                })), 0 === b.indexOf("=")) {
                    var e = l && !/^=[=#]/.test(b);
                    if (b = b.replace(/^=[=#]?|[\s;]*$/g, ""), e) {
                        var f = b.replace(/\s*\([^\)]+\)/, "");
                        n[f] || /^(include|print)$/.test(f) || (b = "$escape(" + b + ")")
                    } else b = "$string(" + b + ")";
                    b = s[1] + b + s[2]
                }
                return g && (b = "$line=" + c + ";" + b), r(a(b), function (a) {
                    if (a && !p[a]) {
                        var b;
                        b = "print" === a ? u : "include" === a ? v : n[a] ? "$utils." + a : o[a] ? "$helpers." + a : "$data." + a, w += a + "=" + b + ",", p[a] = !0
                    }
                }), b + "\n"
            }

            var g = d.debug, h = d.openTag, i = d.closeTag, j = d.parser, k = d.compress, l = d.escape, m = 1,
                    p = {$data: 1, $filename: 1, $utils: 1, $helpers: 1, $out: 1, $line: 1}, q = "".trim,
                    s = q ? ["$out='';", "$out+=", ";", "$out"] : ["$out=[];", "$out.push(", ");", "$out.join('')"],
                    t = q ? "$out+=text;return $out;" : "$out.push(text);",
                    u = "function(){var text=''.concat.apply('',arguments);" + t + "}",
                    v = "function(filename,data){data=data||$data;var text=$utils.$include(filename,data,$filename);" + t + "}",
                    w = "'use strict';var $utils=this,$helpers=$utils.$helpers," + (g ? "$line=0," : ""), x = s[0],
                    y = "return new String(" + s[3] + ");";
            r(c.split(h), function (a) {
                a = a.split(i);
                var b = a[0], c = a[1];
                1 === a.length ? x += e(b) : (x += f(b), c && (x += e(c)))
            });
            var z = w + x + y;
            g && (z = "try{" + z + "}catch(e){throw {filename:$filename,name:'Render Error',message:e.message,line:$line,source:" + b(c) + ".split(/\\n/)[$line-1].replace(/^\\s+/,'')};}");
            try {
                var A = new Function("$data", "$filename", z);
                return A.prototype = n, A
            } catch (a) {
                throw a.temp = "function anonymous($data,$filename) {" + z + "}", a
            }
        }

        var d = function (a, b) {
            return "string" == typeof b ? q(b, {filename: a}) : g(a, b)
        };
        d.version = "3.0.0", d.config = function (a, b) {
            e[a] = b
        };
        var e = d.defaults = {openTag: "<%", closeTag: "%>", escape: !0, cache: !0, compress: !1, parser: null},
                f = d.cache = {};
        d.render = function (a, b) {
            return q(a)(b)
        };
        var g = d.renderFile = function (a, b) {
            var c = d.get(a) || p({filename: a, name: "Render Error", message: "Template not found"});
            return b ? c(b) : c
        };
        d.get = function (a) {
            var b;
            if (f[a]) b = f[a]; else if ("object" == typeof document) {
                var c = document.getElementById(a);
                if (c) {
                    var d = (c.value || c.innerHTML).replace(/^\s*|\s*$/g, "");
                    b = q(d, {filename: a})
                }
            }
            return b
        };
        var h = function (a, b) {
            return "string" != typeof a && (b = typeof a, "number" === b ? a += "" : a = "function" === b ? h(a.call(a)) : ""), a
        }, i = {"<": "&#60;", ">": "&#62;", '"': "&#34;", "'": "&#39;", "&": "&#38;"}, j = function (a) {
            return i[a]
        }, k = function (a) {
            return h(a).replace(/&(?![\w#]+;)|[<>"']/g, j)
        }, l = Array.isArray || function (a) {
            return "[object Array]" === {}.toString.call(a)
        }, m = function (a, b) {
            var c, d;
            if (l(a)) for (c = 0, d = a.length; c < d; c++) b.call(a, a[c], c, a); else for (c in a) b.call(a, a[c], c)
        }, n = d.utils = {$helpers: {}, $include: g, $string: h, $escape: k, $each: m};
        d.helper = function (a, b) {
            o[a] = b
        };
        var o = d.helpers = n.$helpers;
        d.onerror = function (a) {
            var b = "Template Error\n\n";
            for (var c in a) b += "<" + c + ">\n" + a[c] + "\n\n";
            "object" == typeof console && console.error(b)
        };
        var p = function (a) {
                    return d.onerror(a), function () {
                        return "{Template Error}"
                    }
                }, q = d.compile = function (a, b) {
                    function d(c) {
                        try {
                            return new i(c, h) + ""
                        } catch (d) {
                            return b.debug ? p(d)() : (b.debug = !0, q(a, b)(c))
                        }
                    }

                    b = b || {};
                    for (var g in e) void 0 === b[g] && (b[g] = e[g]);
                    var h = b.filename;
                    try {
                        var i = c(a, b)
                    } catch (a) {
                        return a.filename = h || "anonymous", a.name = "Syntax Error", p(a)
                    }
                    return d.prototype = i.prototype, d.toString = function () {
                        return i.toString()
                    }, h && b.cache && (f[h] = d), d
                }, r = n.$each,
                s = "break,case,catch,continue,debugger,default,delete,do,else,false,finally,for,function,if,in,instanceof,new,null,return,switch,this,throw,true,try,typeof,var,void,while,with,abstract,boolean,byte,char,class,const,double,enum,export,extends,final,float,goto,implements,import,int,interface,long,native,package,private,protected,public,short,static,super,synchronized,throws,transient,volatile,arguments,let,yield,undefined",
                t = /\/\*[\w\W]*?\*\/|\/\/[^\n]*\n|\/\/[^\n]*$|"(?:[^"\\]|\\[\w\W])*"|'(?:[^'\\]|\\[\w\W])*'|\s*\.\s*[$\w\.]+/g,
                u = /[^\w$]+/g, v = new RegExp(["\\b" + s.replace(/,/g, "\\b|\\b") + "\\b"].join("|"), "g"),
                w = /^\d[^,]*|,\d[^,]*/g, x = /^,+|,+$/g, y = /^$|,+/;
        e.openTag = "{{", e.closeTag = "}}";
        var z = function (a, b) {
            var c = b.split(":"), d = c.shift(), e = c.join(":") || "";
            return e && (e = ", " + e), "$helpers." + d + "(" + a + e + ")"
        };
        e.parser = function (a, b) {
            a = a.replace(/^\s/, "");
            var c = a.split(" "), e = c.shift(), f = c.join(" ");
            switch (e) {
                case"if":
                    a = "if(" + f + "){";
                    break;
                case"else":
                    c = "if" === c.shift() ? " if(" + c.join(" ") + ")" : "", a = "}else" + c + "{";
                    break;
                case"/if":
                    a = "}";
                    break;
                case"each":
                    var g = c[0] || "$data", h = c[1] || "as", i = c[2] || "$value", j = c[3] || "$index",
                            k = i + "," + j;
                    "as" !== h && (g = "[]"), a = "$each(" + g + ",function(" + k + "){";
                    break;
                case"/each":
                    a = "});";
                    break;
                case"echo":
                    a = "print(" + f + ");";
                    break;
                case"print":
                case"include":
                    a = e + "(" + c.join(",") + ");";
                    break;
                default:
                    if (/^\s*\|\s*[\w\$]/.test(f)) {
                        var l = !0;
                        0 === a.indexOf("#") && (a = a.substr(1), l = !1);
                        for (var m = 0, n = a.split("|"), o = n.length, p = n[m++]; m < o; m++) p = z(p, n[m]);
                        a = (l ? "=" : "=#") + p
                    } else a = d.helpers[e] ? "=#" + e + "(" + c.join(",") + ");" : "=" + a
            }
            return a
        }, "function" == typeof define ? define(function () {
            return d
        }) : "undefined" != typeof exports ? module.exports = d : this.template = d
    }();
</script>

<script>
    var Jdata =${api};

    $(document).ready(function () {

        template.helper("formatjson", function (data) {
            return formatJson(data);
        });
        template.helper("urlcharts", function (data) {
            return urlcharts(data);
        });
        var navhtml = template("sidenav-temp", Jdata);
        $("#wrapper").html(navhtml);

        $("#wrapper").on("click", ".navbar-side li > a", function () {
            if ($(this).hasClass("listitem")) {
                var index = $("#wrapper .listitem").index(this);
                $(".page-wrapper .pageitem").eq(index).show().siblings().hide();
            } else if ($(this).hasClass("home")) {
                $(this).parent("li").addClass("active").siblings().removeClass("active").find("ul").slideUp();
                $(".page-wrapper .pagewelcom").show().siblings(".pageitem").hide();
            } else {
                $(this).siblings("ul").slideToggle().parent("li").addClass("active")
                        .siblings().removeClass("active").find("ul").slideUp();
            }
        });

        $("#searchinput").on("keyup paste", function () {
            var txt = $(this).val();
            $(".nav li").hide();
            var list = [];
            $("#wrapper .listitem").each(function (index) {
                var title = $(this).text();
                if (title.indexOf(txt) > -1) {
                    list.push(index);
                }
            });
            $.each(list, function (index, value) {
                $("#wrapper .listitem").eq(value).show().parents("li").show();
            });
            var minIndex = Math.min.apply(null, list);
            $("#wrapper .listitem").eq(minIndex).parents("ul").show().parents("li").addClass("active");
        });
    });

    function sidebarToggle() {
        $("#wrapper").toggleClass("sidebarhidden");
    }

    function testSend(target) {
        var url = $(target).data("url");
        var dataArray = $(target).parents(".form").find(":input").serializeArray();
        var d = {},
                type = "post";
        $.each(dataArray, function (key, value) {
            var dataName = value.name;
            var dataValue = value.value;
            if (dataName.indexOf("sendType-") < 0) {
                d[dataName] = dataValue;
            } else {
                type = dataValue;
            }
        });
        $(target).parents(".form").find(".userdefined .form-group").each(function () {
            var k = $(this).find(".control-key input").val();
            if ($.trim(k)) {
                d[k] = $(this).find(".inputbox input").val();
            }
        });
      <#if requestType == "json">
     if (type == "post") {
         d = JSON.stringify(d);
     }
      </#if>
        $.ajax({
            url: url,
            type: type,
          <#if requestType == "json">
        contentType: "application/json",
          </#if>
            data: d,
            dataType: "json",
            success: function (data) {
                var jsonhtml = formatJson(data);
                $(target).parents(".form").siblings(".form-content").html(jsonhtml);
            },
            error: function (data) {
                var jsonhtml = formatJson(data);
                $(target).parents(".form").siblings(".form-content").html(jsonhtml);
            }
        });
    }

    function reset(target) {
        $(target).parents(".form").find(".form-control").val("");
        $(target).parents(".form").find("input[type=radio][value='post']").prop("checked", true);
    }

    function userDefined(target) {
        var definedhtml = "<div class=\"form-group\"><div class=\"control-key\">";
        definedhtml += "<input type=\"text\" class=\"form-control\" placeholder=\"key\">";
        definedhtml += "</div><div class=\"inputbox\">";
        definedhtml += "<input type=\"text\" class=\"form-control\" placeholder=\"value\">";
        definedhtml += "<a href=\"javascript:\" class=\"close\" onclick=\"removeFormGroup(this);\">X</a>";
        definedhtml += "</div></div>";
        $(target).parents(".form").find(".userdefined").append(definedhtml);
    }

    function removeFormGroup(target) {
        $(target).parents(".form-group").remove();
    }

    function formatJson(json) {
        if (!json) {
            return "";
        }
        var reg = null,
                formatted = "<div class=\"codepanel\">",
                pad = 0,
                PADDING = "<span class=\"codespaces\"></span>";
        if (typeof json !== "string") {
            json = JSON.stringify(json);
        } else {
            json = eval('(' + json + ')');
            json = JSON.stringify(json);
        }
        reg = /(\{)/g;
        json = json.replace(reg, "$1\r\n");
        reg = /(\})/g;
        json = json.replace(reg, "\r\n$1\r\n");
        reg = /(\,)/g;
        json = json.replace(reg, "$1\r\n");
        reg = /\r\n\r\n/g;
        json = json.replace(reg, "\r\n");
        reg = /\r\n\,/g;
        json = json.replace(reg, ",");
        reg = /\,\r\n\{/g;
        json = json.replace(reg, ",&nbsp;{");
        reg = /\:/g;
        json = json.replace(reg, ":&nbsp;");
        reg = /\:&nbsp;\r\n\{/g;
        json = json.replace(reg, ":&nbsp;{");
        reg = /\:&nbsp;\r\n\[/g;
        json = json.replace(reg, ":&nbsp;[");
        reg = /\}\r\n\]/g;
        json = json.replace(reg, "}]");
        reg = /\}\r\n\"/g;
        json = json.replace(reg, "}\"");
        reg = /\]\r\n\"/g;
        json = json.replace(reg, "]\"");

        $.each(json.split("\r\n"), function (index, node) {
            var i = 0,
                    indent = 0,
                    padding = "";
            if (node.match(/^\}\,&nbsp;\{$/)) {
                if (pad !== 0) {
                    pad--;
                }
                indent = 1;
            } else if (node.match(/\{$/)) {
                indent = 1;
            } else if (node.match(/\}/)) {
                if (pad !== 0) {
                    pad--;
                }
            } else {
                indent = 0;
            }

            for (i = 0; i < pad; i++) {
                padding += PADDING;
            }
            var subcode = node.split(":");
            var codekey = "";
            if (subcode[0].match(/[\{\[]$/) || subcode[0].match(/^[\}\]]/)) {
                codekey += subcode[0];
            } else {
                codekey += "<span class=\"codekey\">" + subcode[0] + "</span>";
            }
            if (subcode[1]) {
                if (subcode[1].match(/\{$/) || subcode[1].match(/\[$/)) {
                    codekey += ":" + subcode[1];
                } else {
                    var codevalue = node.replace(subcode[0] + ":", "");
                    codevalue = codevalue.replace(/\:&nbsp;/g, ":");
                    codekey += ":<span class=\"codeval\">" + codevalue + "</span>";
                }
            }

            formatted += "<p>" + padding + codekey + "</p>";
            pad += indent;
        });
        formatted += "</div>";
        return formatted;
    };

    function urlcharts(apis) {
        if (apis.length) {
            var requestTime = [];
            $.each(apis, function (index, value) {
                requestTime.push(value.requestTime);
            });
            var maxTime = Math.max.apply(null, requestTime);
            var chartMax = Math.ceil(maxTime / 1000);
            var chartHtml = "";
            chartHtml += "<div class=\"urlchart-timebar\">";
            chartHtml += "<span>" + (chartMax / 4) + "s</span>";
            chartHtml += "<span>" + (chartMax / 2) + "s</span>";
            chartHtml += "<span>" + (chartMax * 3 / 4) + "s</span>";
            chartHtml += "<span>" + chartMax + "s</span>";
            chartHtml += "</div>";
            $.each(apis, function (index, value) {
                chartHtml += "<div class=\"urlchart-item\">";
                chartHtml += "<div class=\"urlchart-title\">" + value.title + " (<span>" + value.requestTime + "ms</span>)</div>";
                if (value.requestTime) {
                    var barWidth = value.requestTime / (chartMax * 10);
                } else {
                    var barWidth = 0;
                }
                chartHtml += "<div class=\"urlchart-bar\"><span style=\"width:" + barWidth + "%;\"></span></div>";
                chartHtml += "</div>";
            });
            return chartHtml;
        }
    }
</script>

</body>

</html>