import { Badge, Button, Card, Col, Modal } from 'react-bootstrap';
import React from 'react';

const MyVerticallyCenteredModal = ({ name, price, onHide, show }) => (
  <Modal
    show={show}
    size='lg'
    aria-labelledby='contained-modal-title-vcenter'
    centered
  >
    <Modal.Header>
      <Modal.Title>Dodano do koszyka</Modal.Title>
    </Modal.Header>
    <Modal.Body>
      <p>
        Produkt {name} w cenie {price} został dodany.
      </p>
    </Modal.Body>
    <Modal.Footer>
      <Button onClick={onHide}>Dalej</Button>
    </Modal.Footer>
  </Modal>
);

export const ProductCard = ({ id, name, price, prod_photo, description }) => {
  const [modalShow, setModalShow] = React.useState(false);

  const addToBasket = () => {
    //POST PRODUCT BY ID TO BASKET API
    //USE ID !!!
    setModalShow(true);
  };

  return (
    <Col>
      <Card
        className='p-1 m-2 '
        border='dark'
        style={{ maxWidth: '18rem', minHeight: '25rem' }}
      >
        <Card.Img
          className='mx-auto'
          variant='top'
          src={prod_photo}
          alt=''
          style={{ width: '10rem', height: '10rem' }}
        />
        <Card.Body className='d-flex flex-column justify-content-between'>
          <Card.Title>
            {name} <Badge variant='primary'>{price} zł</Badge>
          </Card.Title>
          <Card.Text>{description}</Card.Text>
          <Button onClick={addToBasket} variant='outline-primary'>
            Dodaj do koszyka
          </Button>
        </Card.Body>
      </Card>
      <MyVerticallyCenteredModal
        show={modalShow}
        onHide={() => setModalShow(false)}
        name={name}
        price={price}
      />
    </Col>
  );
};
