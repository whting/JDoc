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
                                        <th>类型描术</th>
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

<@bpTree children=$value.respParams />

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
                                        <th>类型描术</th>
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