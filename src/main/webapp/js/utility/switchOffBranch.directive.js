(function () {
    'use strict';
    angular
        .module('eightyDirectives')
        .directive('switchOffBranch', switchOffBranch);

    /**
     * @ngdoc directive
     * @name eightyDirectives.directive:switchOffBranch
     * @restrict A
     *
     * @element link
     *
     * @description
     * The `switchOffBranch` directive allows you to switch off branch in angularBootstrapNavTree when user clicks on link.
     *
     * It doesn't deselect branch only if user opens page in new tab/window (when user uses middle mouse button, Ctrl, Shift or Meta key to open a link)
     *
     */
    switchOffBranch.$inject = ['$rootScope'];
    function switchOffBranch($rootScope) {
        return {
            restrict: 'A',
            link: function(scope, element) {
                link(element);
            }
        };
        function link(element) {
            element.on('click', function(event) {
                if(event.which === 2 || event.ctrlKey || event.metaKey || event.shiftKey) {
                    return;
                }
                $rootScope.$broadcast('switchOffBranch');
            });
        }
    }
})();