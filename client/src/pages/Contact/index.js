import React from 'react';
import {Container, Row, Col, Button, Media, Form, FormControl, Modal} from 'react-bootstrap';
import tel from '../../resources/img/contact/tel.svg';
import mail from '../../resources/img/contact/mail.svg';
import fb from '../../resources/img/contact/fb.svg';
import local from '../../resources/img/contact/local.svg';
import insta from '../../resources/img/contact/insta.svg';
import {ErrorModal} from "../../helpers/error";

export class Contact extends React.Component {
    state = {
        name: '',
        email: '',
        topic: '',
        content: '',
        errormodal: null,
    }

    updateCredentials = (e) => {
        this.setState({ [e.target.name]: e.target.value });
    };

    sendmail = () => {
        const { name, email, topic, content } = this.state;
        if (name.length > 0 && email.length > 0 && topic.length > 0 && content.length > 0) {
            //SEND EMAIL
            this.setState({ errormodal: true }); //IF GOOD
            // this.setState({ showModal: false }); //IF WRONG
        }
        else {this.setState({ errormodal: false });}
    };

    render() {
        const {name, email, topic, content, errormodal} = this.state;
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
                                    <p>bkotarski1@gmail.com</p>
                                    <p>marcink1303@gmail.com</p>
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
                                <FormControl
                                    value={name}
                                    name='name'
                                    onChange={this.updateCredentials}
                                    placeholder='imie'
                                /><br/>
                                <Form.Label>Adres E-mail</Form.Label>
                                <FormControl
                                    value={email}
                                    name='email'
                                    onChange={this.updateCredentials}
                                    placeholder='email'
                                /><br/>
                                <Form.Label>Temat</Form.Label>
                                <FormControl
                                    value={topic}
                                    name='topic'
                                    onChange={this.updateCredentials}
                                    placeholder='temat'
                                /><br/>
                                <Form.Label>Treść</Form.Label>
                                <FormControl
                                    value={content}
                                    name='content'
                                    onChange={this.updateCredentials}
                                    placeholder='tresc'
                                /><br/>
                            </Form.Group>
                            <Button onClick={this.sendmail} variant="primary" type="submit">
                                Wyślij
                            </Button>
                        </Form>
                    </Col>
                </Row>
                {errormodal === true && (
                    <MailModal onHide={() => this.setState({ errormodal: null })} />
                )}
                {errormodal === false && (
                    <ErrorModal onHide={() => this.setState({ errormodal: null })} />
                )}
            </Container>
        );
    }
}

export const MailModal = ({ onHide }) => (
    <Modal size='lg' centered show={true}>
        <Modal.Header>
            <p>Pomyślnie wysłano wiadomość E-mail</p>
            <Button variant='success' onClick={onHide}>
                Dalej
            </Button>
        </Modal.Header>
    </Modal>
);
