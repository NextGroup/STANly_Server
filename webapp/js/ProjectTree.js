/**
 * Created with JetBrains WebStorm.
 * User: YHCRetinaPro
 * Date: 13. 3. 18.
 * Time: 오후 10:22
 * To change this template use File | Settings | File Templates.
 */
var lastID=0;
function draw_project_explorer(prj_name) {
    $(".content-body")
        .jstree({
            "json_data" : {
                "ajax" : {
                    "type": "GET",
                    //"url" : "/Stanly/assets/jstree/_docs/_stanly_data.json",
                    "url" : "/Stanly/component/ProjectTree",
                    "data" : function (n) {
                        //console.log(n.attr ? n.attr("id") : 0);
                        return { Name:prj_name,nodeID : n.attr ? n.attr("id") : 0 };
                    }
                }
            },
            "core" : {
                "animation" : 50
            },
            "plugins" : [ "themes", "json_data", "ui", "hotkeys", "types" ],
            "types" : {
                "types" : {
                    "PROJECT" : {
                        "icon" : {
                            "image" : '/Stanly/images/icon/application.gif'
                        }
                    },
                    "LIBRARY" : {
                        "icon" : {
                            "image" : '/Stanly/images/icon/subsystem.gif'
                        }
                    },
                    "PACKAGESET" : {
                        "icon" : {
                            "image" : '/Stanly/images/icon/tree.gif'
                        }
                    },
                    "PACKAGE" : {
                        "icon" : {
                            "image" : '/Stanly/images/icon/package.gif'
                        }
                    },
                    "CLASS" : {
                        "icon" : {
                            "image" : '/Stanly/images/icon/class.gif'
                        }
                    },
                    "INTERFACE" : {
                        "icon" : {
                            "image" : '/Stanly/images/icon/interface.gif'
                        }
                    },
                    "ANNOTATION" : {
                        "icon" : {
                            "image" : '/Stanly/images/icon/annotation.gif'
                        }
                    },
                    "ENUM" : {
                        "icon" : {
                            "image" : '/Stanly/images/icon/enum.gif'
                        }
                    },
                    "CONSTRUCTOR" : {
                        "icon" : {
                            "image" : '/Stanly/images/icon/constructor.png'
                        }
                    },
                    "METHOD" : {
                        "icon" : {
                            "image" : '/Stanly/images/icon/method.gif'
                        }
                    },
                    "FIELD" : {
                        "icon" : {
                            "image" : '/Stanly/images/icon/field.gif'
                        }
                    }
                }
            }
        })
        .bind("select_node.jstree", function (e, data) {
            //console.log(data.rslt.obj[0].id);
            if(lastID != data.rslt.obj.attr("id")){
                console.log(data.rslt.obj.attr("id"));
                BuildDetailList(data.rslt.obj.attr("id"));
                if(BuildMainView)
                    BuildMainView(data.rslt.obj.attr("id"),data.rslt.obj.find('a')[0].text);
                lastID = data.rslt.obj.attr("id");
            }
        });
    //$("#sidebar").resizable();
}

