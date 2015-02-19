(function(){
    'use strict';
    /*exported isPrintLog */
    angular
        .module('eightyFactories')
        .factory('tagsFactory', tagsFactory);

    tagsFactory.$inject = ['crudFactory'];

    function tagsFactory(crudFactory) {

        var publicMethod = {
            getTopNTags: getTopNTags
        };
        return publicMethod;

        function getTopNTags() {
            var tags = crudFactory.tags().topNTags({limit: tagsTop.topNFromAllTags}).$promise;
            tags.then(undefined, function (error) {
                printLog(error);
            });
            return tags;
        }
    }
})();