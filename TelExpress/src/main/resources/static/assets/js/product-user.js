/////////////////////////////////////////

	// Product Main img Slick
	$('#product-main-img').slick({
        infinite: true,
        speed: 300,
        dots: false,
        arrows: true,
        fade: true,
        asNavFor: '#product-imgs',
      });
    
        // Product imgs Slick
      $('#product-imgs').slick({
        slidesToShow: 3,
        slidesToScroll: 1,
        arrows: true,
        centerMode: true,
        focusOnSelect: true,
            centerPadding: 0,
            vertical: true,
        asNavFor: '#product-main-img',
            responsive: [{
            breakpoint: 991,
            settings: {
                        vertical: false,
                        arrows: false,
                        dots: true,
            }
          },
        ]
      });
    
        // Product img zoom
        var zoomMainProduct = document.getElementById('product-main-img');
        if (zoomMainProduct) {
            $('#product-main-img .product-preview').zoom();
        }
    
        /////////////////////////////////////////


        /////////////////////////////////////////

	