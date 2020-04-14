import React from 'react';
import {Container, Accordion, Card, Button, Form } from 'react-bootstrap';

export class Opinions extends React.Component {
    render() {
        return (
            <Container className='mt-4'>
                    <Accordion defaultActiveKey="1">
                        <Card>
                            <Card.Header>
                                <Accordion.Toggle as={Button} variant="link" eventKey="0">
                                    Dodaj swoją opinię!
                                </Accordion.Toggle>
                            </Card.Header>
                            <Accordion.Collapse eventKey="0">
                                <Card.Body>
                                    <Form>
                                        <Form.Group>
                                            <Form.Label>Treść</Form.Label>
                                            <Form.Control as="textarea" rows="3" /><br/>
                                            <Form.Label>Wystaw ocenę</Form.Label>
                                            <Form.Control as="select" size="sm" custom>
                                                <option>1</option>
                                                <option>2</option>
                                                <option>3</option>
                                                <option>4</option>
                                                <option>5</option>
                                            </Form.Control>
                                        </Form.Group>
                                        <div className="mb-3">
                                            <Form.File id="formcheck-api-regular">
                                                <Form.File.Label>Dodaj zdjęcia do opinii</Form.File.Label>
                                                <Form.File.Input />
                                                <Form.File.Input />
                                                <Form.File.Input />
                                            </Form.File>
                                        </div>
                                        <Button variant="primary" type="submit">
                                            Wyślij
                                        </Button>
                                    </Form>
                                </Card.Body>
                            </Accordion.Collapse>
                        </Card>
                    </Accordion>
            </Container>
        );
    }
}
