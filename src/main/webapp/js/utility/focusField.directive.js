(function () {
    'use strict';
    angular
        .module('eightyDirectives')
        .directive('focusField', focusField);
    /**
     * @ngdoc directive
     * @name eightyDirectives.directive:focusField
     * @restrict A
     *
     * @element input
     *
     * @description
     * The `focusField` directive allows you to set focus on predefined <input> inside a Modal.
     * Also provides support for blur event in AngularJS.
     *
     * @param {Boolean} focus-field — Boolean flag which indicates if tree branch should be deselected
     */
    focusField.$inject = ['$timeout', '$parse'];

    function focusField($timeout, $parse) {
        return {
            restrict: 'A',
            link: link
        };
        function link(scope, element, attrs) {
            var model = $parse(attrs.focusField);
            scope.$watch(model, function(value) {
                if(value) {
                    $timeout(function() {
                        element[0].focus();
                        element[0].selectionStart = element[0].selectionEnd = element[0].value.length;
                    });
                }
            });
            element.bind('blur', function() {
                scope.$apply(model.assign(scope, false));
            });
        }
    }
})();