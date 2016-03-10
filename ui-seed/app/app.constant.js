/**
 * Created by shashank on 3/9/16.
 */

(function() {
    'use strict';

    angular
        .module('movieflix')
        .constant('CONFIG',{
            API_END_POINT: 'http://localhost:8080/febatlas'
        });
})();