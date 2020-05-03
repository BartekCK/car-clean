import React from 'react';
import { Button, Col, Container, Row } from 'react-bootstrap';
import BasketImage from '../../resources/img/basket.png';
import { AddDiv, AddImage, PaymentButton, SingleInputProduct } from '../../components/Basket';
import { clearUserBasket, deleteFromUserBasket, getUserBasket } from '../../helpers/apiCommands';
import {ORDER_PRODUCT} from "../../helpers/orderActions";
import { Redirect } from 'react-router-dom';

export class Basket extends React.Component {
  state = {
    products: [],
    price: 0,
      redirectToOrder: false,
  };

  componentDidMount = () => {
    getUserBasket()
        .then((res) => {
          this.setState({ products: res.data.prods, price: res.data.bill});
          console.log(res);
          console.log(this.state.products)
          console.log(this.state.price)
        })
        .catch((err) => console.log(err));
  };

  deleteFromBasket = async (id) => {
    //POST TO THE API
/*    const temp = this.state.products.filter((product) => product.id !== id); //TEMP TO SHOW HOW IT WILL BE WORKS
    this.setState({ products: temp });*/
    await deleteFromUserBasket(id)
        .then((res) => {
          console.log(res)
          this.setState({products: res.data.prods, price: res.data.bill})
        })
        .catch((err) => console.log(err));
  };

  clearBasket = async () => {
    await clearUserBasket()
        .then((res) => {
          console.log(res)
          this.setState({products: res.data.prods, price: res.data.bill})
        })
        .catch((err) => console.log(err));
  };

    redirectToOrders = () => {
        this.setState({redirectToOrder: `/zamowienia`})
    };

  render() {
    const { products, price, redirectToOrder } = this.state;

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
{/*            {products.map((product) => (
                <SingleInputProduct
                    key={product.id}
                    id={product.id}
                    name={product.name}
                    price={product.price}
                />
            ))}*/}
            <Button onClick={this.clearBasket} variant='danger'>
              Usuń wszystkie produkty z koszyka
            </Button>
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
