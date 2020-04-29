import React from 'react';
import { Container, Row } from 'react-bootstrap';
import styled from 'styled-components';
import { ProductCard } from '../../components/Product';
import { ProductNav } from '../../components/ProductNav';
import {getAllProducts, getProductsByCategory} from "../../helpers/apiCommands";

export class Shop extends React.Component {
  state = {
    products: [],
    activeCategory: '',
      error: null
  };

  componentDidMount = async () => {
        try {
            const result = await getAllProducts();
            this.setState({ products: result });
        } catch (e) {
            const error = { message: 'Brak produktów w sklepie' };
            this.setState({ error: error });
        }
    };

  componentDidUpdate = async (prevProps, prevState) => {
    if (this.state.activeCategory !== prevState.activeCategory) {
        try {
            const result = await getProductsByCategory(this.state.activeCategory);
            this.setState({ products: result });
        } catch (e) {
            const error = { message: 'Brak produktów w sklepie' };
            this.setState({ error: error });
        }
    }
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
