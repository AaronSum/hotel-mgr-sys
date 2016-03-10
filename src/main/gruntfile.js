/*"wrapper" 函数，
包含了整个Grunt
配置信息*/
module.exports = function(grunt) {

    var package = grunt.file.readJSON('package.json'),
        projectContextPath = "webapp",
        timeStamp = new Date().valueOf();

    var homeJspContextPath = "<%=urlPath %>";
    var regJspContextPath = "<%=urlPath %>";
    var jinJspContextPath = "<%=urlPath %>";

    var homeJspFileStr = grunt.file.read("webapp/webpage/home/home.jsp");
    var regJspFileStr = grunt.file.read("webapp/main/reg/reglogin.jsp");
    var jinJspFileStr = grunt.file.read("webapp/webpage/home/home-jin.jsp");

    var homeFilePathMap = {};
    var regFilePathMap = {};
    var jinFilePathMap = {};

    var homeCssList = [];
    var regCssList = [];
    var jinCssList = [];

    var homeJsList = [];
    var regJsList = [];
    var jinJsList = [];

    var homeBlackList = {
        "hms.min.css": true,
        "hms.min.js": true,
        "hms.tmpl.js": true
    };

    var regBlackList = {
        "hms-reg.min.css": true,
        "hms-reg.min.js": true,
        "hms-reg.tmpl.js": true
    };

    var jinBlackList = {
        "hms-jin.min.css": true,
        "hms-jin.min.js": true,
        "hms-jin.tmpl.js": true
    };

    var homeTargetContext = "home";
    var regTargetContext = "reg";
    var jinTargetContext = "jin";
    
    var filterFilesPath = function(options){

        var jsReg = /\s+<script.+?src=[\"\'](.*?)js[\"\'].*?>(.*?)<\/script>/g;
        while ((res = jsReg.exec(options.source)) != null) {
            var jsFilePath = res[1].replace(options.jspContextPath, projectContextPath) + "js";//.replace(/\//g,"\\");
            //grunt.log.writeln(getFileName(jsFilePath));
            var fileName = getFileName(jsFilePath);
            if (!options.filePathMap[jsFilePath] && !options.blackList[fileName]) {
                options.filePathMap[jsFilePath] = true;
                options.jsList.push("dist/" + options.targetContext + "/js/" + fileName);
                // grunt.log.writeln("dist/" + options.targetContext + "/js/" + fileName);
            }
        };
        // grunt.log.writeln(options.jsList.length);

        //grunt.log.writeln("");

        var cssReg = /\s+<link.+?href=[\"\'](.*?)css[\"\'].*?>/g;
        while ((res = cssReg.exec(options.source)) != null) {
            var cssFilePath = res[1].replace(options.jspContextPath, projectContextPath) + "css";//.replace(/\//g,"\\");
            //grunt.log.writeln(getFileName(cssFilePath));
            var fileName = getFileName(cssFilePath);
            if (!options.filePathMap[cssFilePath] && !options.blackList[fileName]) {
                options.filePathMap[cssFilePath] = true;
                options.cssList.push("dist/" + options.targetContext + "/css/" + fileName);
                // grunt.log.writeln("dist/" + options.targetContext + "/css/" + fileName);
            }
        };
        // grunt.log.writeln(options.cssList.length);
    };

    var getFileName = function(path){
        var lastIndex = path.lastIndexOf("/");
        if (lastIndex < 0) {
            return path;
        }
        return path.substring(lastIndex + 1, path.length);
    };

    var getFilePath = function(path){
        var lastIndex = path.lastIndexOf("/");
        if (lastIndex < 0) {
            return "";
        }
        return path.substring(0, lastIndex + 1);
    };

    filterFilesPath({
        source: homeJspFileStr,
        jspContextPath: homeJspContextPath,
        filePathMap: homeFilePathMap,
        blackList: homeBlackList,
        targetContext: homeTargetContext,
        jsList: homeJsList,
        cssList: homeCssList
    });

    filterFilesPath({
        source: regJspFileStr,
        jspContextPath: regJspContextPath,
        filePathMap: regFilePathMap,
        blackList: regBlackList,
        targetContext: regTargetContext,
        jsList: regJsList,
        cssList: regCssList
    });

    filterFilesPath({
        source: jinJspFileStr,
        jspContextPath: jinJspContextPath,
        filePathMap: jinFilePathMap,
        blackList: jinBlackList,
        targetContext: jinTargetContext,
        jsList: jinJsList,
        cssList: jinCssList
    });

    // for (var i=0;i<homeCssList.length;i++) {
    //     grunt.log.writeln(homeCssList[i]);
    // }

    /*在这个函数中，
    我们可以初始化 
    configuration 对象*/
    grunt.initConfig({

        /*Grunt自带的有一个简单的模板引擎用于输出配置对象属性值, 
        由于<% %>模板字符串可以引用任意的配置属性，因此可以通过
        这种方式来指定诸如文件路径和文件列表类型的配置数据，从而
        减少一些重复的工作。你可以在这个配置对象中
        (传递给initConfig()方法的对象)存储任意的数据，只要它不与
        你任务配置所需的属性冲突，否则会被忽略。此外，由于这本身
        就是JavaScript，你不仅限于使用JSON；你可以在这里使用任意
        的有效的JS代码。如果有必要，你甚至可以以编程的方式生成配置。*/

        /*可以从package.json 
        文件读入项目配置信息，
        并存入pkg 属性内*/
        pkg: package,

        /*Clean files and folders*/
        clean: {
            dist: ['dist/']
        },

        /*Copy files*/
        copy: {
            // main: {
            //     files: [
            //         {
            //             expand: true, //如果设为true，就表示下面文件名的占位符（即*号）都要扩展成具体的文件名。
            //             cwd: 'webapp/', // makes all src relative to cwd
            //             src: [
            //                 'css/*', // includes files within path
            //                 'ext/**', // includes files within path and its sub-directories
            //                 'base/vendor/**'
            //             ],
            //             dest: 'dist/',
            //             flatten: false // flattens results to a single level
            //         }
            //         // {
            //         //     expand: true,
            //         //     cwd: 'webapp/', // makes all src relative to cwd
            //         //     nonull: true, // If the file should exist, and non-existence generate an error,
            //         //     src: [
            //         //     ],
            //         //     dest: 'dist/',
            //         //     flatten: true, // flattens results to a single level
            //         //     options: {
            //         //         process: function (content, srcpath) {
            //         //             return content.replace(/[sad ]/g,"_");
            //         //         },
            //         //     }
            //         // }
            //     ]
            // }
            html_home: {
                files:[
                    {
                        expand: true,
                        cwd: 'webapp/main',
                        src: [
                            '**/template/*.html', '!reg/**/*.html', '!**/*-jin*.html', '**/**/hms-position.tmpl.html'
                        ],
                        dest: 'dist/' + homeTargetContext + '/template/main/',
                        filter: 'isFile',
                        flatten: false
                    }
                ]
            },
            html_reg: {
                files:[
                    {
                        expand: true,
                        cwd: 'webapp/main',
                        src: [
                            'reg/**/*.html', '!**/*-jin*.html', '**/**/hms-position.tmpl.html'
                        ],
                        dest: 'dist/' + regTargetContext + '/template/main/',
                        filter: 'isFile',
                        flatten: false
                    }
                ]
            },
            html_jin: {
                files:[
                    {
                        expand: true,
                        cwd: 'webapp/main',
                        src: [
                            '**/template/*.html', '!reg/**/*.html', '**/**/hms-position.tmpl.html'
                        ],
                        dest: 'dist/' + jinTargetContext + '/template/main/',
                        filter: 'isFile',
                        flatten: false
                    }
                ]
            },
            js_home: {
                files:[
                    // {
                    //     expand: true,
                    //     cwd: 'webapp',
                    //     src: [
                    //         'js/*.js', '!js/router-config-jin.js'
                    //     ],
                    //     dest: 'dist/js/',
                    //     filter: 'isFile',
                    //     flatten: true
                    // },
                    // {
                    //     expand: true,
                    //     cwd: 'webapp/main',
                    //     src: [
                    //         '**/*.js', '!reg/**/*.js', '!**/*-jin*.js'
                    //     ],
                    //     dest: 'dist/js/',
                    //     filter: 'isFile',
                    //     flatten: true
                    // },
                    {
                        expand: true,
                        cwd: 'webapp',
                        src: [
                            '**/*.js'
                        ],
                        dest: 'dist/' + homeTargetContext + '/js/',
                        filter: function(filePath) {
                            var filePath = filePath.replace(/\\/g,"/");
                            // grunt.log.writeln(filePath);
                            if (homeFilePathMap[filePath]) {
                                return true;
                            }
                            return false;
                        },
                        flatten: true
                    }
                ]
            },
            js_reg: {
                files:[
                    // {
                    //     expand: true,
                    //     cwd: 'webapp',
                    //     src: [
                    //         'js/*.js', '!js/router-config-jin.js'
                    //     ],
                    //     dest: 'dist/js/',
                    //     filter: 'isFile',
                    //     flatten: true
                    // },
                    // {
                    //     expand: true,
                    //     cwd: 'webapp/main',
                    //     src: [
                    //         '**/*.js', '!reg/**/*.js', '!**/*-jin*.js'
                    //     ],
                    //     dest: 'dist/js/',
                    //     filter: 'isFile',
                    //     flatten: true
                    // },
                    {
                        expand: true,
                        cwd: 'webapp',
                        src: [
                            '**/*.js'
                        ],
                        dest: 'dist/' + regTargetContext + '/js/',
                        filter: function(filePath) {
                            var filePath = filePath.replace(/\\/g,"/");
                            // grunt.log.writeln(filePath);
                            if (regFilePathMap[filePath]) {
                                return true;
                            }
                            return false;
                        },
                        flatten: true
                    }
                ]
            },
            js_jin: {
                files:[
                    // {
                    //     expand: true,
                    //     cwd: 'webapp',
                    //     src: [
                    //         'js/*.js', '!js/router-config-jin.js'
                    //     ],
                    //     dest: 'dist/js/',
                    //     filter: 'isFile',
                    //     flatten: true
                    // },
                    // {
                    //     expand: true,
                    //     cwd: 'webapp/main',
                    //     src: [
                    //         '**/*.js', '!reg/**/*.js', '!**/*-jin*.js'
                    //     ],
                    //     dest: 'dist/js/',
                    //     filter: 'isFile',
                    //     flatten: true
                    // },
                    {
                        expand: true,
                        cwd: 'webapp',
                        src: [
                            '**/*.js'
                        ],
                        dest: 'dist/' + jinTargetContext + '/js/',
                        filter: function(filePath) {
                            var filePath = filePath.replace(/\\/g,"/");
                            // grunt.log.writeln(filePath);
                            if (jinFilePathMap[filePath]) {
                                return true;
                            }
                            return false;
                        },
                        flatten: true
                    }
                ]
            },
            css_home: {
                files:[
                    {
                        expand: true,
                        cwd: 'webapp',
                        src: [
                            '**/*.css'
                        ],
                        dest: 'dist/' + homeTargetContext + '/css/',
                        filter: function(filePath) {
                            var filePath = filePath.replace(/\\/g,"/");
                            // grunt.log.writeln(filePath);
                            if (homeFilePathMap[filePath]) {
                                return true;
                            }
                            return false;
                        },
                        flatten: true
                    }
                ],
                options: {
                    process: function (content, srcpath) {
                        var cssFPath = getFilePath(srcpath);
                        // grunt.log.writeln(cssFPath);
                        var newContent = content;


                        var urlReg = /url\s*\((.*?)\)/g;
                        while ((res = urlReg.exec(content)) != null) {

                            var urlPath = res[1].trim();
                            var pathPre = "";

                            try {
                                if (urlPath.indexOf("\"") == 0 || urlPath.indexOf("\'") == 0) {
                                    urlPath = urlPath.substring(1, urlPath.length - 1)
                                }
                            } catch(e) {
                                grunt.log.writeln(e);
                            }


                            if (urlPath.indexOf("/") == 0 || urlPath.indexOf("http") == 0) {
                                continue;
                            }

                            if (urlPath.indexOf("./") == 0) {
                                urlPath = urlPath.substring(2, urlPath.length);
                            }

                            urlPath = "./../.." + cssFPath.replace("webapp", "") + urlPath;
                            newContent = newContent.replace(res[0], "url('" + urlPath + "')");
                            // grunt.log.writeln(urlPath);
                        };

                        return newContent;
                    }
                }
            },
            css_reg: {
                files:[
                    {
                        expand: true,
                        cwd: 'webapp',
                        src: [
                            '**/*.css'
                        ],
                        dest: 'dist/' + regTargetContext + '/css/',
                        filter: function(filePath) {
                            var filePath = filePath.replace(/\\/g,"/");
                            // grunt.log.writeln(filePath);
                            if (regFilePathMap[filePath]) {
                                return true;
                            }
                            return false;
                        },
                        flatten: true
                    }
                ],
                options: {
                    process: function (content, srcpath) {
                        var cssFPath = getFilePath(srcpath);
                        // grunt.log.writeln(cssFPath);
                        var newContent = content;


                        var urlReg = /url\s*\((.*?)\)/g;
                        while ((res = urlReg.exec(content)) != null) {

                            var urlPath = res[1].trim();
                            var pathPre = "";

                            try {
                                if (urlPath.indexOf("\"") == 0 || urlPath.indexOf("\'") == 0) {
                                    urlPath = urlPath.substring(1, urlPath.length - 1)
                                }
                            } catch(e) {
                                grunt.log.writeln(e);
                            }


                            if (urlPath.indexOf("/") == 0 || urlPath.indexOf("http") == 0) {
                                continue;
                            }

                            if (urlPath.indexOf("./") == 0) {
                                urlPath = urlPath.substring(2, urlPath.length);
                            }

                            urlPath = "./../.." + cssFPath.replace("webapp", "") + urlPath;
                            newContent = newContent.replace(res[0], "url('" + urlPath + "')");
                            // grunt.log.writeln(urlPath);
                        };

                        return newContent;
                    }
                }
            },
            css_jin: {
                files:[
                    {
                        expand: true,
                        cwd: 'webapp',
                        src: [
                            '**/*.css'
                        ],
                        dest: 'dist/' + jinTargetContext + '/css/',
                        filter: function(filePath) {
                            var filePath = filePath.replace(/\\/g,"/");
                            // grunt.log.writeln(filePath);
                            if (jinFilePathMap[filePath]) {
                                return true;
                            }
                            return false;
                        },
                        flatten: true
                    }
                ],
                options: {
                    process: function (content, srcpath) {
                        var cssFPath = getFilePath(srcpath);
                        // grunt.log.writeln(cssFPath);
                        var newContent = content;


                        var urlReg = /url\s*\((.*?)\)/g;
                        while ((res = urlReg.exec(content)) != null) {

                            var urlPath = res[1].trim();
                            var pathPre = "";

                            try {
                                if (urlPath.indexOf("\"") == 0 || urlPath.indexOf("\'") == 0) {
                                    urlPath = urlPath.substring(1, urlPath.length - 1)
                                }
                            } catch(e) {
                                grunt.log.writeln(e);
                            }


                            if (urlPath.indexOf("/") == 0 || urlPath.indexOf("http") == 0) {
                                continue;
                            }

                            if (urlPath.indexOf("./") == 0) {
                                urlPath = urlPath.substring(2, urlPath.length);
                            }

                            urlPath = "./../.." + cssFPath.replace("webapp", "") + urlPath;
                            newContent = newContent.replace(res[0], "url('" + urlPath + "')");
                            // grunt.log.writeln(urlPath);
                        };

                        return newContent;
                    }
                }
            },
            img: {
                files:[]
            }
        },

        // jshint: {
        //     options: { // http://jshint.com/docs/options/
        //         asi: false,
        //         es5: true, // ECMAScript 5.1代码标准
        //         debug: true,
        //         bitwise: true, // 位运算
        //         boss: false,
        //         curly: true, // 总是用花括号
        //         eqeqeq: false, // 必须使用“===”
        //         eqnull: true, // 不会提醒“==null”这种表达式
        //         browser: true, //
        //         globals: { //
        //             jQuery: true //
        //         },
        //         evil: false,
        //         forin: true, // 不循环原型继承的属性
        //         immed: false,
        //         laxbreak: false,
        //         newcap: false,
        //         freeze: true, // 不扩展原始对象属性
        //         funcscope: false, // 不忽略块外调用
        //         futurehostile: true, // 提醒未实现的将来的新对象或方法
        //         iterator: false,
        //         latedef: true,
        //         maxcomplexity: 100,
        //         maxdepth: 10,
        //         maxerr: 0,
        //         maxparams: 5,
        //         maxstatements: 10,
        //         noarg: true,
        //         nocomma: true,
        //         nonbsp: true,
        //         nonew: true,
        //         notypeof: false,
        //         noempty: false,
        //         nomen: false,
        //         shadow: false,
        //         singleGroups: true,
        //         strict: true,
        //         undef: true,
        //         unused: true,
        //         onevar: true,
        //         passfail: true,
        //         plusplus: false,
        //         regexp: false,
        //         sub: true,
        //         white: true
        //     },
        //     uses_defaults: jsList,
        //     with_overrides: {
        //         options: {
        //             curly: false,
        //             undef: true,
        //         },
        //         files: {
        //             src: []
        //         },
        //     }
        // },

        // csslint: {
        //     strict: {
        //         src: ['css/*.css']
        //     },
        //     lax: {
        //         options: {
        //             // csslintrc: '.csslintrc'
        //         },
        //         src: cssList
        //     }
        // },

        ngtemplates: {
            hmsApp_home: {
                // cwd: 'dist',
                src: ['dist/' + homeTargetContext + '/template/**/*.html'],
                dest: 'webapp/js/grunt/hms.tmpl.js',
                options: {
                    htmlmin: { //https://github.com/kangax/html-minifier#options-quick-reference
                        removeComments:                 true,
                        removeCommentsFromCDATA:        true,
                        removeCDATASectionsFromCDATA:   true,
                        collapseWhitespace:             true,
                        conservativeCollapse:           true,
                        preserveLineBreaks:             true,
                        collapseBooleanAttributes:      false,
                        removeAttributeQuotes:          false,
                        removeRedundantAttributes:      true,
                        preventAttributesEscaping:      true,
                        useShortDoctype:                true,
                        removeEmptyAttributes:          false,
                        removeScriptTypeAttributes:     false,
                        removeStyleLinkTypeAttributes:  true,
                        removeOptionalTags:             false,
                        removeIgnored:                  false,
                        removeEmptyElements:            false,
                        keepClosingSlash:               true,
                        caseSensitive:                  true,
                        minifyJS: {
                            mangle: true
                        },
                        minifyCSS:                      true,
                        minifyURLs:                     false
                    }
                }
            },
            hmsApp_reg: {
                // cwd: 'dist',
                src: ['dist/' + regTargetContext + '/template/**/*.html'],
                dest: 'webapp/js/grunt/hms-reg.tmpl.js',
                options: {
                    htmlmin: { //https://github.com/kangax/html-minifier#options-quick-reference
                        removeComments:                 true,
                        removeCommentsFromCDATA:        true,
                        removeCDATASectionsFromCDATA:   true,
                        collapseWhitespace:             true,
                        conservativeCollapse:           true,
                        preserveLineBreaks:             true,
                        collapseBooleanAttributes:      false,
                        removeAttributeQuotes:          false,
                        removeRedundantAttributes:      true,
                        preventAttributesEscaping:      true,
                        useShortDoctype:                true,
                        removeEmptyAttributes:          false,
                        removeScriptTypeAttributes:     false,
                        removeStyleLinkTypeAttributes:  true,
                        removeOptionalTags:             false,
                        removeIgnored:                  false,
                        removeEmptyElements:            false,
                        keepClosingSlash:               true,
                        caseSensitive:                  true,
                        minifyJS: {
                            mangle: true
                        },
                        minifyCSS:                      true,
                        minifyURLs:                     false
                    }
                }
            },
            hmsApp_jin: {
                // cwd: 'dist',
                src: ['dist/' + jinTargetContext + '/template/**/*.html'],
                dest: 'webapp/js/grunt/hms-jin.tmpl.js',
                options: {
                    htmlmin: { //https://github.com/kangax/html-minifier#options-quick-reference
                        removeComments:                 true,
                        removeCommentsFromCDATA:        true,
                        removeCDATASectionsFromCDATA:   true,
                        collapseWhitespace:             true,
                        conservativeCollapse:           true,
                        preserveLineBreaks:             true,
                        collapseBooleanAttributes:      false,
                        removeAttributeQuotes:          false,
                        removeRedundantAttributes:      true,
                        preventAttributesEscaping:      true,
                        useShortDoctype:                true,
                        removeEmptyAttributes:          false,
                        removeScriptTypeAttributes:     false,
                        removeStyleLinkTypeAttributes:  true,
                        removeOptionalTags:             false,
                        removeIgnored:                  false,
                        removeEmptyElements:            false,
                        keepClosingSlash:               true,
                        caseSensitive:                  true,
                        minifyJS: {
                            mangle: true
                        },
                        minifyCSS:                      true,
                        minifyURLs:                     false
                    }
                }
            }
        },

        'string-replace': {
            htmlTmplUrlTask_home: {
                files: {'webapp/js/grunt/hms.tmpl.js': 'webapp/js/grunt/hms.tmpl.js'},
                options: {
                    replacements: [
                        {
                            pattern: /put\('dist\/home\/template/g,
                            replacement: "put(contentPath + '"
                        },
                        {
                            pattern: "hmsApp_home",
                            replacement: "hmsApp"
                        }
                    ]
                }
            },
            htmlTmplUrlTask_reg: {
                files: {'webapp/js/grunt/hms-reg.tmpl.js': 'webapp/js/grunt/hms-reg.tmpl.js'},
                options: {
                    replacements: [
                        {
                            pattern: /put\('dist\/reg\/template/g,
                            replacement: "put(contentPath + '"
                        },
                        {
                            pattern: "hmsApp_reg",
                            replacement: "hmsApp"
                        }
                    ]
                }
            },
            htmlTmplUrlTask_jin: {
                files: {'webapp/js/grunt/hms-jin.tmpl.js': 'webapp/js/grunt/hms-jin.tmpl.js'},
                options: {
                    replacements: [
                        {
                            pattern: /put\('dist\/jin\/template/g,
                            replacement: "put(contentPath + '"
                        },
                        {
                            pattern: "hmsApp_jin",
                            replacement: "hmsApp"
                        }
                    ]
                }
            },
            // cssUrlTask1: {
            //     files: {'dist/css/bootstrap.min.css': 'dist/css/bootstrap.min.css'},
            //     options: {
            //         replacements: [
            //             {
            //                 pattern: /src:url\(.*?\/fonts\//g,
            //                 replacement: 'src:url(./../../main/plug-in/bootstrap/bootstrap-3.3.4/fonts/'
            //             },
            //             {
            //                 pattern: /,url\(.*?\/fonts\//g,
            //                 replacement: ',url(./../../main/plug-in/bootstrap/bootstrap-3.3.4/fonts/'
            //             }
            //         ]
            //     }
            // },
            // cssUrlTask2: {
            //     files: {'dist/css/font-awesome.min.css': 'dist/css/font-awesome.min.css'},
            //     options: {
            //         replacements: [
            //             {
            //                 pattern: /src:url\(.*?\/fonts\//g,
            //                 replacement: "src:url('./../../main/plug-in/font-awesome-4.3.0/fonts/"
            //             },
            //             {
            //                 pattern: /,url\(.*?\/fonts\//g,
            //                 replacement: ",url('./../../main/plug-in/font-awesome-4.3.0/fonts/"
            //             }
            //         ]
            //     }
            // },
            // jspTask_home: {
            //     files: {
            //         'webapp/webpage/home/home.jsp': 'webapp/webpage/home/home.jsp'
            //     },
            //     options: {
            //         replacements: [
            //             {
            //                 pattern: /\?version=.*?"/g,
            //                 replacement: '"'
            //             }
            //         ]
            //     }
            // },
            // jspTask_reg: {
            //     files: {
            //         'webapp/main/reg/reglogin.jsp': 'webapp/main/reg/reglogin.jsp'
            //     },
            //     options: {
            //         replacements: [
            //             {
            //                 pattern: /\?version=.*?"/g,
            //                 replacement: '"'
            //             }
            //         ]
            //     }
            // },
            // jspTask_jin: {
            //     files: {
            //         'webapp/webpage/home/home-jin.jsp': 'webapp/webpage/home/home-jin.jsp'
            //     },
            //     options: {
            //         replacements: [
            //             {
            //                 pattern: /\?version=.*?"/g,
            //                 replacement: '"'
            //             }
            //         ]
            //     }
            // }
            versionJspTask: {
                files: {
                    'webapp/webpage/include/version.jsp': 'webapp/webpage/include/version.jsp'
                },
                options: {
                    replacements: [
                        {
                            pattern: /_v_(.*)"/g,
                            replacement: '_v_"'
                        }
                    ]
                }
            }
        },

        cssmin: { // https://github.com/jakubpawlowicz/clean-css#how-to-use-clean-css-programmatically
            options: {
                advanced: false,
                aggressiveMerging: false,
                keepSpecialComments: 0//,
                // rebase: true
            },
            compress: {
                files: {
                    'webapp/js/grunt/hms.min.css': homeCssList,
                    'webapp/js/grunt/hms-reg.min.css': regCssList,
                    'webapp/js/grunt/hms-jin.min.css': jinCssList,
                }
            }
        },


        /*concat任务将所有存
        在于src/目录下以.js结
        尾的文件合并起来，然
        后存储在dist目录中，
        并以项目名来命名*/
        concat: {
            home:{
                options: {
                    // 定义一个用于插入合并输出文件之间的字符
                    separator: ';\r\n',
                    stripBanners: true
                },
                // 将要被合并的文件
                src: homeJsList,
                // 合并后的JS文件的存放位置
                dest: 'dist/' + homeTargetContext + '/<%= pkg.name %>.js'
            },
            reg:{
                options: {
                    // 定义一个用于插入合并输出文件之间的字符
                    separator: ';\r\n',
                    stripBanners: true
                },
                // 将要被合并的文件
                src: regJsList,
                // 合并后的JS文件的存放位置
                dest: 'dist/' + regTargetContext + '/<%= pkg.name %>-reg.js'
            },
            jin:{
                options: {
                    // 定义一个用于插入合并输出文件之间的字符
                    separator: ';\r\n',
                    stripBanners: true
                },
                // 将要被合并的文件
                src: jinJsList,
                // 合并后的JS文件的存放位置
                dest: 'dist/' + jinTargetContext + '/<%= pkg.name %>-jin.js'
            }
        },

        /*uglify在dist/目录
        中创建了一个包含压缩
        结果的JavaScript文件*/
        uglify: {
            home:{
                options: {
                    // 此处定义的banner注释将插入到输出文件的顶部
                    banner: '/*! <%= pkg.name %> <%= grunt.template.today("dd-mm-yyyy") %> */\n',
                    mangle: true
                },
                files: {
                    'webapp/js/grunt/<%= pkg.name %>.min.js': ['<%= concat.home.dest %>'] //uglify会自动压缩concat任务中生成的文件
                }
            },
            reg:{
                options: {
                    // 此处定义的banner注释将插入到输出文件的顶部
                    banner: '/*! <%= pkg.name %> <%= grunt.template.today("dd-mm-yyyy") %> */\n',
                    mangle: true
                },
                files: {
                    'webapp/js/grunt/<%= pkg.name %>-reg.min.js': ['<%= concat.reg.dest %>'] //uglify会自动压缩concat任务中生成的文件
                }
            },
            jin:{
                options: {
                    // 此处定义的banner注释将插入到输出文件的顶部
                    banner: '/*! <%= pkg.name %> <%= grunt.template.today("dd-mm-yyyy") %> */\n',
                    mangle: true
                },
                files: {
                    'webapp/js/grunt/<%= pkg.name %>-jin.min.js': ['<%= concat.jin.dest %>'] //uglify会自动压缩concat任务中生成的文件
                }
            }
        },

        //图片压缩image压缩 - imagemin
        //配置插件(图片压缩)
        //http://www.hdwill.info/post/2014/gruntjs-imagemin
        // imagemin: {
        //     dynamic: {
        //         options: {
        //             optimizationLevel: 3 // png图片优化水平，3是默认值，取值区间0-7
        //         },
        //         files: [
        //             {
        //                 expand: true, // 开启动态扩展
        //                 cwd: "images/", // 当前工作路径
        //                 src: ["**/*.{png,jpg,gif}"], // 要出处理的文件格式(images下的所有png,jpg,gif)
        //                 dest: "images/" // 输出目录(直接覆盖原图)
        //             }
        //         ]
        //     }
        // },

        cachebreaker: {
            jsp: {
                options: {
                    match: ['_v_*'],
                    position: "overwrite",
                    replacement: function (){
                        return package.version + "_" + timeStamp + '"';
                    }
                },
                files: {
                    src: ['webapp/webpage/include/version.jsp']
                }
            },
            jspTask_home: {
                options: {
                    match: ['hms.min.css*', 'hms.min.js*', 'hms.tmpl.js*'],
                    position: "overwrite",
                    replacement: function (){
                        return "?version=" + package.version + "_" + timeStamp + '"';
                    }
                },
                files: {
                    src: ['webapp/webpage/home/home.jsp']
                }
            },
            jspTask_reg: {
                options: {
                    match: ['hms-reg.min.css*', 'hms-reg.min.js*', 'hms-reg.tmpl.js*'],
                    position: "overwrite",
                    replacement: function (){
                        return "?version=" + package.version + "_" + timeStamp + '"';
                    }
                },
                files: {
                    src: ['webapp/main/reg/reglogin.jsp']
                }
            },
            jspTask_jin: {
                options: {
                    match: ['hms-jin.min.css*', 'hms-jin.min.js*', 'hms-jin.tmpl.js*'],
                    position: "overwrite",
                    replacement: function (){
                        return "?version=" + package.version + "_" + timeStamp + '"';
                    }
                },
                files: {
                    src: ['webapp/webpage/home/home-jin.jsp']
                }
            },
            iframeTask: {
                options: {
                    match: ['/main/plug-in/gaode/gaodeMap.html*', '/main/plug-in/gaode/gaodeMapTools.html*'],
                    position: "overwrite",
                    replacement: function (){
                        return "?version=" + package.version + "_" + timeStamp + '"';
                    }
                },
                files: {
                    // src: [
                    //     'dist/**/hotel-peripheral.html',
                    //     'dist/**/hotel-traffic.html',
                    //     'dist/**/hms-position.tmpl.html'
                    // ]
                    src: [
                        'webapp/js/grunt/<%= pkg.name %>.min.js',
                        'webapp/js/grunt/<%= pkg.name %>-reg.min.js',
                        'webapp/js/grunt/<%= pkg.name %>-jin.min.js'
                    ]
                }
            }
        }

    });

    /*加载所需要的Grunt
    插件。 它们应该已经
    全部通过npm安装好了*/
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-copy');
    // grunt.loadNpmTasks('grunt-contrib-jshint');
    // grunt.loadNpmTasks('grunt-contrib-csslint');
    grunt.loadNpmTasks('grunt-angular-templates');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    // grunt.loadNpmTasks('grunt-contrib-imagemin');
    grunt.loadNpmTasks('grunt-string-replace');
    grunt.loadNpmTasks('grunt-cache-breaker');

    /*在命令行上输入
    "grunt hms"，hms task
    就会被执行,在 taskList 
    中指定的每个任务都会按
    照其出现的顺序依次执行*/
    grunt.registerTask('hms', [
        'clean', 
        'copy', 
        'ngtemplates', 
        'string-replace', 
        'cssmin', 
        'concat', 
        'uglify', 
        // 'cachebreaker:jspTask_home',
        // 'cachebreaker:jspTask_reg',
        // 'cachebreaker:jspTask_jin',
        // 'cachebreaker:iframeTask',
        'cachebreaker:jsp'
    ]);
};