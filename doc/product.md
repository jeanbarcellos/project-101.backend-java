

```java

    private static final String MSG_ERROR_PRODUCT_OUT_OF_STOCK = "Produto '%s' sem estoque";

    public ProductResponse updateStock(UUID id, Integer quantity) {
        this.validateExistsById(id);

        if (quantity < Product.QUANTITY_EMPTY) {
            throw new ValidationException(
                    String.format("O quantidade do estoque não pode ser menor que %s", Product.QUANTITY_EMPTY));
        }

        var product = this.findByIdOrThrow(id);

        product.setQuantity(quantity);

        product = this.productRepository.save(product);

        return ProductResponse.of(product);
    }

    public ProductResponse addStock(UUID id, Integer quantity) {
        this.validateExistsById(id);

        if (quantity < Product.QUANTITY_MIN_ADD) {
            throw new ValidationException(
                    String.format("O quantidade minima de produtos a serem adicionados ao estoque é de %s",
                            Product.QUANTITY_MIN_ADD));
        }

        var product = this.findByIdOrThrow(id);

        product.addQuantity(quantity);

        product = this.productRepository.save(product);

        return ProductResponse.of(product);
    }

    public ProductResponse subStoke(UUID id, Integer quantity) {
        this.validateExistsById(id);

        var product = this.findByIdOrThrow(id);

        if (!product.hasQuantity(quantity)) {
            throw new ValidationException(String.format(MSG_ERROR_PRODUCT_OUT_OF_STOCK, product.getName()));
        }

        product.subQuantity(quantity);

        product = this.productRepository.save(product);

        return ProductResponse.of(product);
    }
```