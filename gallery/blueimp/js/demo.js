/* global blueimp, $ */

$(function () {
  'use strict'

  // Load demo images from flickr:
  $.ajax({
    url: 'http://jirsis.local:3000/albums/example'
  }).done(function (result) {
    var linksContainer = $('#links');
    var baseUrl;
    var thumbnailUrl;
    // Add the demo images as links with thumbnails to the page:
    $.each(result.photos, function (index, photo) {
      baseUrl = photo.original.image;
      thumbnailUrl = photo.thumbnail.image;
      $('<a/>')
        .append($('<img>').prop('src', thumbnailUrl))
        .prop('href', baseUrl)
        .prop('title', (index+1)+'/'+result.photos.length)
        .attr('data-gallery', '')
        .appendTo(linksContainer)
    })
  });
})
