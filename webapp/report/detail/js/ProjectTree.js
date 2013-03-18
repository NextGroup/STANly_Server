/**
 * Created with JetBrains WebStorm.
 * User: YHCRetinaPro
 * Date: 13. 3. 18.
 * Time: 오후 10:22
 * To change this template use File | Settings | File Templates.
 */
$(function () {
    $("#prjtree")
        .jstree({
            "json_data" : {
                "ajax" : {
                    "type": "GET",
                    //"url" : "/Stanly/assets/jstree/_docs/_stanly_data.json",
                    "url" : "/Stanly/component/ProjectTree",
                    "data" : function (n) {
                        //console.log(n.attr ? n.attr("id") : 0);
                        return { Name:getProjectName(),nodeID : n.attr ? n.attr("id") : 0 };
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
                            "image" : '/Stanly/assets/jstree/img/application.gif'
                        }
                    },
                    "LIBRARY" : {
                        "icon" : {
                            "image" : '/Stanly/assets/jstree/img/subsystem.gif'
                        }
                    },
                    "PACKAGESET" : {
                        "icon" : {
                            "image" : '/Stanly/assets/jstree/img/tree.gif'
                        }
                    },
                    "PACKAGE" : {
                        "icon" : {
                            "image" : '/Stanly/assets/jstree/img/package.gif'
                        }
                    },
                    "CLASS" : {
                        "icon" : {
                            "image" : '/Stanly/assets/jstree/img/class.gif'
                        }
                    },
                    "INTERFACE" : {
                        "icon" : {
                            "image" : '/Stanly/assets/jstree/img/interface.gif'
                        }
                    },
                    "ANNOTATION" : {
                        "icon" : {
                            "image" : '/Stanly/assets/jstree/img/annotation.gif'
                        }
                    },
                    "ENUM" : {
                        "icon" : {
                            "image" : '/Stanly/assets/jstree/img/enum.gif'
                        }
                    },
                    "CONSTRUCTOR" : {
                        "icon" : {
                            "image" : '/Stanly/assets/jstree/img/constructor.png'
                        }
                    },
                    "METHOD" : {
                        "icon" : {
                            "image" : '/Stanly/assets/jstree/img/method.gif'
                        }
                    },
                    "FIELD" : {
                        "icon" : {
                            "image" : '/Stanly/assets/jstree/img/field.gif'
                        }
                    }
                }
            }
        })
        .bind("select_node.jstree", function (e, data) {
            //console.log(data.rslt.obj[0].id);
            console.log(data.rslt.obj.attr("id"));
            BuildDetailList();
        });

});