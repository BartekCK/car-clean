import React from 'react';
import { Container, Row, Col, Button, Media, Form } from 'react-bootstrap';
import tel from '../../resources/img/contact/tel.svg';
import mail from '../../resources/img/contact/mail.svg';
import fb from '../../resources/img/contact/fb.svg';
import local from '../../resources/img/contact/local.svg';
import insta from '../../resources/img/contact/insta.svg';

export class Contact extends React.Component {
    render() {
        return (
            <Container className='mt-4'>
                <Row>
                    <Col>
                        <>
                            <Media>
                                <img
                                    width={64}
                                    height={64}
                                    className="align-Ąself-start mr-3"
                                    src= {local}
                                    alt="Generic placeholder"
                                />
                                <Media.Body>
                                    <h5>Gdzie się mieścimy</h5>
                                    <p>25-666 Kielce</p>
                                    <p>Ul. Czysta 23</p>
                                    <p>Polska</p>
                                </Media.Body>
                            </Media>
                            <br/>
                            <Media>
                                <img
                                    width={64}
                                    height={64}
                                    className="align-self-start mr-3"
                                    src= {tel}
                                    alt="Generic placeholder"
                                />
                                <Media.Body>
                                    <h5>Telefon</h5>
                                    <p>Stacjonarny: +41 45 36 234</p>
                                    <p>Komórkowy: 567 345 234</p>
                                </Media.Body>
                            </Media>
                            <br/>
                            <Media>
                                <img
                                    width={64}
                                    height={64}
                                    className="align-self-start mr-3"
                                    src= {mail}
                                    alt="Generic placeholder"
                                />
                                <Media.Body>
                                    <h5>E-mail</h5>
                                    <p>marycc.gorek@gmai.com</p>
                                    <p>michalina.conv2@gmai.com</p>
                                </Media.Body>
                            </Media>
                            <br/>
                            <Media>
                                <img
                                    width={64}
                                    height={64}
                                    className="align-self-end mr-3"
                                    src= {fb}
                                    alt="Generic placeholder"
                                />
                                <Media.Body>
                                    <h5>Facebook</h5>
                                    <a href={"/sklep"}><p>link</p></a>
                                </Media.Body>
                            </Media>
                            <br/>
                            <Media>
                                <img
                                    width={64}
                                    height={64}
                                    className="align-Ąself-end mr-3"
                                    src= {insta}
                                    alt="Generic placeholder"
                                />
                                <Media.Body>
                                    <h5>Instagram</h5>
                                    <p>carcleankielce</p>
                                </Media.Body>
                            </Media>
                        </>
                    </Col>
                    <Col>
                        <h3>Masz pytania? Napisz do nas</h3>
                        <Form>
                            <Form.Group controlId="formBasicEmail">
                                <Form.Label>Imie</Form.Label>
                                <Form.Control type="text"/><br/>
                                <Form.Label>Adres E-mail</Form.Label>
                                <Form.Control type="email"/><br/>
                                <Form.Label>Temat</Form.Label>
                                <Form.Control type="text"/><br/>
                                <Form.Label>Treść</Form.Label>
                                <Form.Control as="textarea" rows="3" />
                            </Form.Group>
                            <Button variant="primary" type="submit">
                                Wyślij
                            </Button>
                        </Form>
                    </Col>
                </Row>
            </Container>
        );
    }
}
