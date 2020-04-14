import React from 'react';
import {Container, Row} from 'react-bootstrap';
import styled from 'styled-components';
import {ProductCard} from '../../components/Product';
import {ProductNav} from '../../components/ProductNav';

export class Shop extends React.Component {
  state = {
    products: [
      //TEMP DATA INSIDE
      {
        id: 0,
        name: 'Piana K2',
        price: 20,
        prod_photo: 'https://mirapolnext.pl/images/26547.jpg',
        description: 'Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
      },
      {
        id: 1,
        name: 'Piana K2',
        price: 0,
        prod_photo: 'https://mirapolnext.pl/images/26547.jpg',
        description: 'Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
      },
      {
        id: 2,
        name: 'Piana K2',
        price: 0,
        prod_photo: 'https://mirapolnext.pl/images/26547.jpg',
        description: 'Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
      },
      {
        id: 3,
        name: 'Piana K2',
        price: 0,
        prod_photo: 'https://mirapolnext.pl/images/26547.jpg',
        description: 'Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
      },
      {
        id: 4,
        name: 'Piana K2',
        price: 0,
        prod_photo: 'https://mirapolnext.pl/images/26547.jpg',
        description: 'Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum',
      },
    ],
    activeCategory: '',
  };

  componentDidMount = () => {
    //GET FROM API
  };
  componentDidUpdate = () => {
    //GET FROM API WHEN CATEGORY CHANGE
  };

  setProductCategory = (chooseCategory) => {
    this.setState({ activeCategory: chooseCategory });
  };

  render() {
    const { products, activeCategory } = this.state;
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
