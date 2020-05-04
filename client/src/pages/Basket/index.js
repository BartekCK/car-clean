import React, { useEffect, useState } from 'react';
import { Button, Col, Container, Row } from 'react-bootstrap';
import BasketImage from '../../resources/img/basket.png';
import { AddDiv, AddImage, SingleInputProduct } from '../../components/Basket';
import {useHistory} from 'react-router-dom'
import {
  clearUserBasket,
  createUserOrderProducts,
  deleteFromUserBasket,
  getUserBasket,
} from '../../helpers/apiCommands';
import { ORDER_PRODUCT } from '../../helpers/orderActions';

export const Basket = () => {
  const [data, setData] = useState({
    products: [],
    price: 0,
  });

  const history = useHistory();

  useEffect(() => {
    console.log('start');
    getUserBasket()
      .then((res) => {
        setData({ products: res.data.prods, price: res.data.bill });
      })
      .catch((err) => console.log(err));
  }, []);

  const deleteFromBasket = async (id) => {
    if (data.products.length > 0) {
      await deleteFromUserBasket(id)
        .then((res) => {
          console.log(res);
          setData({ ...data, products: res.data.prods, price: res.data.bill });
        })
        .catch((err) => console.log(err));
    }
  };

  const clearBasket = async () => {
    if (data.products.length > 0) {
      await clearUserBasket()
        .then((res) => {
          console.log(res);
          setData({ ...data, products: res.data.prods, price: res.data.bill });
        })
        .catch((err) => console.log(err));
    }
  };

  const createOrder = async () => {
    if (data.products.length > 0) {
      try {
        const result = await createUserOrderProducts();
        console.log(result);
        history.push(`/payments/${ORDER_PRODUCT}/${result.data.id}`)
      } catch (e) {
        console.log(e);
      }
    }
  };

  return (
    <Container className='shadow'>
      <Row xs={1} sm={2}>
        <Col className='p-3 d-flex flex-column justify-content-center align-items-center'>
          {data.products.length > 0 ? (
            data.products.map((product,mapId) => (
              <SingleInputProduct
                key={mapId}
                id={product.id}
                name={product.name}
                price={product.price}
                deleteProd={deleteFromBasket}
              />
            ))
          ) : (
            <>
              <AddImage src={BasketImage} />
              <h3>Koszyk jest pusty</h3>
              <hr />
            </>
          )}
          <Button onClick={clearBasket} variant='danger'>
            Usuń wszystkie produkty z koszyka
          </Button>
        </Col>
        <Col className='p-3 d-flex flex-column justify-content-between align-items-center'>
          <h3>Kwota do zapłaty : {data.price} zł</h3>
          <AddDiv />
          <Button
            onClick={createOrder}
            className='mr-1 align-self-stretch'
            variant='primary'
          >
            Zapłać
          </Button>
        </Col>
      </Row>
    </Container>
  );
};
