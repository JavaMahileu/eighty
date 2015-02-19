(function () {
    'use strict';
    angular
        .module('eightyDirectives')
        .directive('abnTree', abnTree);
    /**
     * @ngdoc directive
     * @name angularBootstrapNavTree.directive:abnTree
     * @restrict E
     *
     * @description
     * This is a Tree directive for Angular JS apps that use Bootstrap CSS.
     * `treeControl` has the following methods for tree control:
     *
     * - `{void}` `expand_all()` — Expands all branches.
     * - `{void}` `expand_all()` — Collapsed all branches.
     * - `{object}` `get_first_branch()` — Returns first branch.
     * - `{object}` `select_first_branch()` — Selects first branch and returns it.
     * - `{object}` `get_selected_branch()` — Returns selected branch.
     * - `{object}` `get_parent_branch({object} branch)` — Returns parent branch.
     * - `{object}` `select_branch({object} branch)` — Selects the given branch and returns it.
     * - `{array}` `get_children({object} branch)` — Returns an array of child branches for the given one.
     * - `{object}` `select_parent_branch({object} branch)` — Selects parent branch for the given one and returns it.
     * - `{object}` `add_branch({object} parent, {object} new_branch)` — Adds `new_branch` to `parent` and returns it.
     * - `{object}` `add_root_branch({object} new_branch)` — Adds `new_branch` to root and returns it.
     * - `{object}` `expand_branch({object} branch)` — Expands the given branch and returns it.
     * - `{object}` `collapse_branch({object} branch)` — Collapsed the given branch and returns it.
     * - `{object}` `get_first_child({object} branch)` — Returns the first child for the given branch.
     * - `{object}` `get_next_branch({object} branch)` — Returns the next branch to the given one.
     * - `{object}` `select_next_branch({object} branch)` — Selects and returns the next branch to the given one.
     * - `{object}` `last_descendant({object} branch)` — Returns the last descendant branch for the given one.
     * - `{object}` `get_prev_branch({object} branch)` — Returns the previous branch to the given one.
     * - `{object}` `select_prev_branch({object} branch)` — Selects and returns the previous branch to the given one.
     *
     * @element OPTION
     * @param {String} tree-data Data for tree
     * @param {String=} tree-control Bind treeControl variable to specified
     * @param {String=} on-select Bind onSelect function to specified
     * @param {String=} edit-folder Bind editFolder function to specified
     * @param {String=} add-folder Bind addFolder function to specified
     * @param {String=} delete-folder Bind deleteFolder function to specified
     * @param {String=} refresh-branch Bind refreshBranch function to specified
     * @param {String=} editable Bind editable variable to specified
     * @param {String=} children Name for children objects
     * @param {String=} icon-expand Class for expand icon
     * @param {String=} icon-collapse Class for collapse icon
     * @param {String=} icon-leaf Class for file icon
     * @param {String=} icon-add-folder Class for add folder icon
     * @param {String=} icon-delete-folder Class for delete folder/file icon
     * @param {String=} icon-edit-folder Class for edit folder/file icon
     * @param {String=} expand-level The initial expand level
     */

    abnTree.$inject = ['$timeout'];
    function abnTree($timeout) {
        return {
            restrict: 'E',
            templateUrl: 'pages/treeNavTemplate.html',
            replace: true,
            scope: {
                treeData: '=',
                onSelect: '&',
                initialSelection: '@',
                treeControl: '=',
                editFolder: '&',
                addFolder: '&',
                deleteFolder: '&',
                refreshBranch: '&',
                editable: '='
            },
            link: function(scope, element, attrs) {
                link(scope, attrs);
            }
        };

        function link(scope, attrs) {
            var children = attrs.children || 'children';

            var expand_all_parents, expand_level, for_all_ancestors, for_each_branch, get_parent, n, on_treeData_change, select_branch, selected_branch, tree;

            scope.$on('switchOffBranch', function() {
                if (selected_branch) {
                    selected_branch.selected = false;
                    selected_branch = null;
                }
            });

            scope.onTreeIconClick = function (branch) {
                if (!branch.expanded) {
                    $timeout(function () {
                        return scope.refreshBranch({
                            branch: branch
                        });
                    });
                } else {
                    branch.expanded = !branch.expanded;
                }
            };

            if (attrs.iconExpand == null) {
                attrs.iconExpand = 'icon-plus  glyphicon glyphicon-plus  fa fa-plus';
            }
            if (attrs.iconCollapse == null) {
                attrs.iconCollapse = 'icon-minus glyphicon glyphicon-minus fa fa-minus';
            }
            if (attrs.iconLeaf == null) {
                attrs.iconLeaf = 'icon-file  glyphicon glyphicon-file  fa fa-file';
            }

            if (attrs.iconAddFolder == null) {
                attrs.iconAddFolder = 'icon-file  glyphicon glyphicon-plus fa fa-file';
            }

            if (attrs.iconDeleteFolder == null) {
                attrs.iconDeleteFolder = 'icon-file  glyphicon glyphicon-remove  fa fa-file';
            }

            if (attrs.iconEditFolder == null) {
                attrs.iconEditFolder = 'icon-file  glyphicon glyphicon-pencil  fa fa-file';
            }

            scope.editIcons = {
                addFolder: attrs.iconAddFolder,
                deleteFolder: attrs.iconDeleteFolder,
                editFolder: attrs.iconEditFolder
            };

            if (attrs.expandLevel == null) {
                attrs.expandLevel = '1';
            }
            expand_level = parseInt(attrs.expandLevel,
                10);
            if (!scope.treeData) {
                console.log('Error: no treeData defined for the tree!');
                return;
            }
            if (scope.treeData.length == null) {
                if (treeData.title != null) {
                    scope.treeData = [treeData];
                } else {
                    console.log('Error: treeData should be an array of root branches');
                    return;
                }
            }
            for_each_branch = function (f) {
                var do_f, root_branch, _i, _len, _ref, _results;
                do_f = function (branch, level) {
                    var child, _i, _len, _ref, _results;
                    f(branch, level);
                    if (branch[children] != null) {
                        _ref = branch[children];
                        _results = [];
                        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
                            child = _ref[_i];
                            _results.push(do_f(child, level + 1));
                        }
                        return _results;
                    }
                };
                _ref = scope.treeData;
                _results = [];
                for (_i = 0, _len = _ref.length; _i < _len; _i++) {
                    root_branch = _ref[_i];
                    _results.push(do_f(root_branch, 1));
                }
                return _results;
            };
            selected_branch = null;
            select_branch = function (branch) {
                if (!branch) {
                    if (selected_branch != null) {
                        selected_branch.selected = false;
                    }
                    selected_branch = null;
                    return;
                }
                if (branch !== selected_branch) {
                    if (selected_branch != null) {
                        selected_branch.selected = false;
                    }
                    branch.selected = true;
                    selected_branch = branch;
                    expand_all_parents(branch);
                    if (branch.onSelect != null) {
                        return $timeout(function () {
                            return branch.onSelect(branch);
                        });
                    } else {
                        if (scope.onSelect != null) {
                            return $timeout(function () {
                                return scope.onSelect({
                                    branch: branch
                                });
                            });
                        }
                    }
                }
            };
            scope.user_clicks_branch = function (branch) {
                if (branch !== selected_branch) {
                    return select_branch(branch);
                }
            };
            get_parent = function (child) {
                var parent;
                parent = void 0;
                if (child.parent_uid) {
                    for_each_branch(function (b) {
                        if (b.uid === child.parent_uid) {
                            parent = b;
                            return parent;
                        }
                    });
                }
                return parent;
            };
            for_all_ancestors = function (child, fn) {
                var parent;
                parent = get_parent(child);
                if (parent != null) {
                    fn(parent);
                    return for_all_ancestors(parent, fn);
                }
            };
            expand_all_parents = function (child) {
                return for_all_ancestors(child,
                    function (b) {
                        b.expanded = true;
                        return b.expanded;
                    });
            };
            scope.tree_rows = [];
            on_treeData_change = function () {
                var add_branch_to_list, root_branch, _i, _len, _ref, _results;
                for_each_branch(function (b) {
                    if (!b.uid) {
                        b.uid = '' + Math.random();
                        return b.uid;
                    }
                });
                for_each_branch(function (b) {
                    var child, _i, _len, _ref, _results;
                    if (angular.isArray(b[children])) {
                        _ref = b[children];
                        _results = [];
                        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
                            child = _ref[_i];
                            _results.push(child.parent_uid = b.uid);
                        }
                        return _results;
                    }
                });
                scope.tree_rows = [];
                for_each_branch(function (branch) {
                    var child, f;
                    if (branch[children]) {
                        if (branch[children].length > 0) {
                            f = function (e) {
                                if (typeof e === 'string') {
                                    return {
                                        title: e,
                                        children: []
                                    };
                                } else {
                                    return e;
                                }
                            };
                            branch[children] = (function () {
                                var _i, _len, _ref, _results;
                                _ref = branch[children];
                                _results = [];
                                for (_i = 0, _len = _ref.length; _i < _len; _i++) {
                                    child = _ref[_i];
                                    _results.push(f(child));
                                }
                                return _results;
                            })();
                            return branch[children];
                        }
                    } else {
                        branch[children] = [];
                        return branch[children];
                    }
                });
                add_branch_to_list = function (level, branch, visible) {
                    var child, child_visible, tree_icon, _i, _len, _ref, _results;
                    if (branch.expanded == null) {
                        branch.expanded = false;
                    }
                    if (!branch[children] || branch[children].length === 0) {
                        tree_icon = attrs.iconLeaf;
                    } else {
                        if (branch.expanded) {
                            tree_icon = attrs.iconCollapse;
                        } else {
                            tree_icon = attrs.iconExpand;
                        }
                    }
                    scope.tree_rows.push({
                        level: level,
                        branch: branch,
                        title: branch.title,
                        tree_icon: tree_icon,
                        visible: visible
                    });
                    if (branch[children] != null) {
                        _ref = branch[children];
                        _results = [];
                        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
                            child = _ref[_i];
                            child_visible = visible && branch.expanded;
                            _results.push(add_branch_to_list(level + 1, child, child_visible));
                        }
                        return _results;
                    }
                };
                _ref = scope.treeData;
                _results = [];
                for (_i = 0, _len = _ref.length; _i < _len; _i++) {
                    root_branch = _ref[_i];
                    _results.push(add_branch_to_list(1, root_branch, true));
                }
                return _results;
            };
            scope.$watch('treeData',
                on_treeData_change, true);
            if (attrs.initialSelection != null) {
                for_each_branch(function (b) {
                    if (b.title === attrs.initialSelection) {
                        return $timeout(function () {
                            return select_branch(b);
                        });
                    }
                });
            }
            n = scope.treeData.length;
            for_each_branch(function (b, level) {
                b.level = level;
                b.expanded = b.level < expand_level;
                return b.expanded;
            });
            if ((scope.treeControl !== null) && (angular.isObject(scope.treeControl))) {
                tree = scope.treeControl;
                tree.expand_all = function () {
                    return for_each_branch(function (b) {
                        b.expanded = true;
                        return b.expanded;
                    });
                };
                tree.collapse_all = function () {
                    return for_each_branch(function (b) {
                        b.expanded = false;
                        return b.expanded;
                    });
                };
                tree.get_first_branch = function () {
                    n = scope.treeData.length;
                    if (n > 0) {
                        return scope.treeData[0];
                    }
                };
                tree.select_first_branch = function () {
                    var b;
                    b = tree.get_first_branch();
                    return tree.select_branch(b);
                };
                tree.get_selected_branch = function () {
                    return selected_branch;
                };
                tree.get_parent_branch = function (b) {
                    return get_parent(b);
                };
                tree.select_branch = function (b) {
                    select_branch(b);
                    return b;
                };
                tree.get_children = function (b) {
                    return b[children];
                };
                tree.select_parent_branch = function (b) {
                    var p;
                    if (b == null) {
                        b = tree.get_selected_branch();
                    }
                    if (b != null) {
                        p = tree.get_parent_branch(b);
                        if (p != null) {
                            tree.select_branch(p);
                            return p;
                        }
                    }
                };
                tree.add_branch = function (parent, new_branch) {
                    if (parent != null) {
                        parent[children].push(new_branch);
                        parent.expanded = true;
                    } else {
                        scope.treeData.push(new_branch);
                    }
                    return new_branch;
                };
                tree.add_root_branch = function (new_branch) {
                    tree.add_branch(null, new_branch);
                    return new_branch;
                };
                tree.expand_branch = function (b) {
                    if (b == null) {
                        b = tree.get_selected_branch();
                    }
                    if (b != null) {
                        b.expanded = true;
                        return b;
                    }
                };
                tree.collapse_branch = function (b) {
                    if (b == null) {
                        b = selected_branch;
                    }
                    if (b != null) {
                        b.expanded = false;
                        return b;
                    }
                };
                tree.get_siblings = function (b) {
                    var p, siblings;
                    if (b == null) {
                        b = selected_branch;
                    }
                    if (b != null) {
                        p = tree.get_parent_branch(b);
                        if (p) {
                            siblings = p[children];
                        } else {
                            siblings = scope.treeData;
                        }
                        return siblings;
                    }
                };
                tree.get_next_sibling = function (b) {
                    var i, siblings;
                    if (b == null) {
                        b = selected_branch;
                    }
                    if (b != null) {
                        siblings = tree.get_siblings(b);
                        n = siblings.length;
                        i = siblings.indexOf(b);
                        if (i < n) {
                            return siblings[i + 1];
                        }
                    }
                };
                tree.get_prev_sibling = function (b) {
                    var i, siblings;
                    if (b == null) {
                        b = selected_branch;
                    }
                    siblings = tree.get_siblings(b);
                    n = siblings.length;
                    i = siblings.indexOf(b);
                    if (i > 0) {
                        return siblings[i - 1];
                    }
                };
                tree.select_next_sibling = function (b) {
                    var next;
                    if (b == null) {
                        b = selected_branch;
                    }
                    if (b != null) {
                        next = tree.get_next_sibling(b);
                        if (next != null) {
                            return tree.select_branch(next);
                        }
                    }
                };
                tree.select_prev_sibling = function (b) {
                    var prev;
                    if (b == null) {
                        b = selected_branch;
                    }
                    if (b != null) {
                        prev = tree.get_prev_sibling(b);
                        if (prev != null) {
                            return tree.select_branch(prev);
                        }
                    }
                };
                tree.get_first_child = function (b) {
                    var _ref;
                    if (b == null) {
                        b = selected_branch;
                    }
                    if (b != null) {
                        if (((_ref = b[children]) != null ? _ref.length : void 0) > 0) {
                            return b[children][0];
                        }
                    }
                };
                tree.get_closest_ancestor_next_sibling = function (b) {
                    var next, parent;
                    next = tree.get_next_sibling(b);
                    if (next != null) {
                        return next;
                    } else {
                        parent = tree.get_parent_branch(b);
                        return tree.get_closest_ancestor_next_sibling(parent);
                    }
                };
                tree.get_next_branch = function (b) {
                    var next;
                    if (b == null) {
                        b = selected_branch;
                    }
                    if (b != null) {
                        next = tree.get_first_child(b);
                        if (next != null) {
                            return next;
                        } else {
                            next = tree.get_closest_ancestor_next_sibling(b);
                            return next;
                        }
                    }
                };
                tree.select_next_branch = function (b) {
                    var next;
                    if (b == null) {
                        b = selected_branch;
                    }
                    if (b != null) {
                        next = tree.get_next_branch(b);
                        if (next != null) {
                            tree.select_branch(next);
                            return next;
                        }
                    }
                };
                tree.last_descendant = function (b) {
                    var last_child;
                    if (b == null) {
                        console.log('debugger');
                    }
                    n = b[children].length;
                    if (n === 0) {
                        return b;
                    } else {
                        last_child = b[children][n - 1];
                        return tree.last_descendant(last_child);
                    }
                };
                tree.get_prev_branch = function (b) {
                    var parent, prev_sibling;
                    if (b == null) {
                        b = selected_branch;
                    }
                    if (b != null) {
                        prev_sibling = tree.get_prev_sibling(b);
                        if (prev_sibling != null) {
                            return tree.last_descendant(prev_sibling);
                        } else {
                            parent = tree.get_parent_branch(b);
                            return parent;
                        }
                    }
                };
                tree.select_prev_branch = function (b) {
                    var prev;
                    if (b == null) {
                        b = selected_branch;
                    }
                    if (b != null) {
                        prev = tree.get_prev_branch(b);
                        if (prev != null) {
                            tree.select_branch(prev);
                            return prev;
                        }
                    }
                };
                return tree.select_prev_branch;
            }
        }
    }
})();
