import React from 'react';
import {Container, Row} from 'react-bootstrap';
import styled from 'styled-components';
import {ProductCard} from '../../components/Product';
import {ProductNav} from '../../components/ProductNav';

export class Shop extends React.Component {

state = {
  products: [],
  activeCategory: ''
};

    componentDidMount = () => {
      fetch('http://localhost:8080/api/v1/product')
          .then(res => res.json())
          .then(products => {
            console.log(products);
              this.setState({products});
    }
          );
    };

  componentDidUpdate = (prevProps, prevState) => {
    if(this.state.activeCategory!==prevState.activeCategory) {
        fetch(`http://localhost:8080/api/v1/product/category/${this.state.activeCategory}`)
            .then(res => res.json())
            .then(products => {
                    console.log(products);
                    this.setState({products});
                }
            );
    }
};

  setProductCategory = (chooseCategory) => {
    this.setState({activeCategory: chooseCategory});
  };

  render() {
    const {products, activeCategory} = this.state;
      return (
          <ShopContainer>
            <ProductNav
                setProductCategory={this.setProductCategory}
                activeCategory={activeCategory}
            />
            <Container className='shadow my-3'>
              <Row xs={1} sm={2} md={3} lg={4}>
                {products.map((product) => (
                    <ProductCard
                        key={product.id}
                        id={product.id}
                        name={product.name}
                        price={product.price}
                        prod_photo={product.prod_photo}
                        description={product.description}
                    />
                ))}
              </Row>
            </Container>
          </ShopContainer>
      );
    }
}

const ShopContainer = styled.div`
  display: flex;
  width: 100vw;
  flex-direction: column;
`;
