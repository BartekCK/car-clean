import React from 'react';
import { Col, Container, Row } from 'react-bootstrap';
import BasketImage from '../../resources/img/basket.png';
import { AddDiv, AddImage, PaymentButton, SingleInputProduct } from '../../components/Basket';

export class Basket extends React.Component {
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
    price: 0,
  };

  componentDidMount = () => {
    // GET FROM API USER BASKET WITH PRODUCTS AND PRICE
  };

  deleteFromBasket = (id) => {
    //POST TO THE API
    const temp = this.state.products.filter((product) => product.id !== id); //TEMP TO SHOW HOW IT WILL BE WORKS
    this.setState({ products: temp });
  };

  render() {
    const { products, price } = this.state;

    return (
      <Container className='shadow'>
        <Row xs={1} sm={2}>
          <Col className='p-3 d-flex flex-column justify-content-center align-items-center'>
            {products.length > 0 ? (
              products.map((product) => (
                <SingleInputProduct
                  key={product.id}
                  id={product.id}
                  name={product.name}
                  price={product.price}
                  deleteProd={this.deleteFromBasket}
                />
              ))
            ) : (
              <>
                <AddImage src={BasketImage} />
                <h3>Koszyk jest pusty</h3>
                <hr />
              </>
            )}
          </Col>
          <Col className='p-3 d-flex flex-column justify-content-between align-items-center'>
            <h3>Kwota do zapłaty : {price} zł</h3>
            <AddDiv />
            <PaymentButton />
          </Col>
        </Row>
      </Container>
    );
  }
}
