(function () {
    'use strict';
    angular
        .module('eightyDirectives')
        .directive('questionsContent', questionsContent);

    questionsContent.$inject = ['$window'];
    function questionsContent($window) {
        return {
            restrict: 'C',
            link: link
        };
        function link(scope, elem, attrs) {
            scope.getWrapperHeight = function () {
                return $('.wrapper').outerHeight();
            };
            var headerHeight = $('.questions-header').offset().top + $('.questions-header').outerHeight(true);
            var footerHeight = $('.questions-footer').outerHeight(true);

            scope.$watch(scope.getWrapperHeight, function (newValue) {
                elem.css('max-height', newValue - headerHeight - footerHeight + 'px');
            }, true);
            angular.element($window).bind('resize', function () {
                scope.$apply();
            });

            var raw = elem[0];
            elem.bind('scroll', function() {
                if (raw.scrollTop + raw.offsetHeight >= raw.scrollHeight) {
                    scope.$apply(attrs.whenScrolled);
                }
            });
        }
    }
})();