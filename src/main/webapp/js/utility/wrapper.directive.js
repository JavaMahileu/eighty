(function () {
    'use strict';
    angular
        .module('eightyDirectives')
        .directive('wrapper', wrapper);

    wrapper.$inject = ['$window'];
    function wrapper($window) {
        return {
            restrict: 'C',
            link: link
        };
        function link(scope, elem) {
            scope.getwinHeight = function () {
                return angular.element($window).height();
            };
            var headerHeight = $('header').outerHeight(true);
            var footerHeight = $('footer').outerHeight(true);
            scope.$watch(scope.getwinHeight, function (newValue) {
                elem.css('max-height', newValue - headerHeight - footerHeight + 'px');
            }, true);
            angular.element($window).bind('resize', function () {
                scope.$apply();
            });
        }
    }
})();