(function(){
    'use strict';
    angular
        .module('eightyControllers')
        .controller('tagsCtrl', tagsCtrl);

    tagsCtrl.$inject = ['$scope', 'tagsFactory', 'tagsList'];

    function tagsCtrl($scope, tagsFactory, tagsList) {
        /*jshint validthis:true*/
        var vm = this;
        vm.tagsTop = tagsList;

        $scope.$on('topTags-update', function() {
            tagsFactory.getTopNTags().then(function (tags) {
                vm.tagsTop = tags;
            });
        });
    }
})();