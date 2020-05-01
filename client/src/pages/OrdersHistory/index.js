import React from 'react';
import {Button, Container, Modal, Table} from 'react-bootstrap';
import {getUserOrderProducts, changeStatusUserOrderProducts} from "../../helpers/apiCommands";

const OrderDetailsModal = ({onHide, show, id }) => (
    <Modal
        show={show}
        size='lg'
        aria-labelledby='contained-modal-title-vcenter'
        centered
    >
        <Modal.Header>
            <Modal.Title>Szczegóły zamówienia</Modal.Title>
        </Modal.Header>
        <Modal.Body>
            <h6>Zamówione produkty:</h6>
            <ul>
                {id.map(i => <li key={i.id}>{i.name}<br/> Cena: {i.price} <br/>Kategoria: {i.category}<br/></li>)}
            </ul>
        </Modal.Body>
        <Modal.Footer>
            <Button onClick={onHide}>Zamknij</Button>
        </Modal.Footer>
    </Modal>
);

export class OrderHistory extends React.Component {
    state = {
        orders: [],
        modalShow: false,
        tempOrder: []
    };

    componentDidMount = async () => {
        //GET ORDERS FROM API
        try {
            const result = await getUserOrderProducts();
            console.log(result);
            this.setState({ orders: result.data });
        } catch (e) {
            const error = { message: 'Brak zamówień' };
            this.setState({ error: error });
        }
    };

    changeOrderStatus = async (id) => {
        //CHANGE STATUS OF ORDER, PAY
        try {
            const result = await changeStatusUserOrderProducts(id);
            console.log(result);
        } catch (e) {
            const error = { message: 'Błąd' };
            this.setState({ error: error });
        }
    };

    showModal =(prods) => {
        console.log(prods)
        this.setState({tempOrder: prods, modalShow: true})
    };

    render() {
        const { orders, modalShow, tempOrder } = this.state;

        return (
            <Container className='shadow'>
                <h1>Historia zamówień</h1>
                <Table striped bordered hover>
                    <thead>
                    <tr>
                        <th>Numer zamówienia</th>
                        <th>Zamówienie</th>
                        <th>Kwota do zapłaty</th>
                        <th>Status</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        orders.length?
                            orders.map(order =>
                                <tr key={order.id}>
                                    <th>{order.id}</th>
                                    <th>
                                        {/*{order.prods.map(prod => <p key={prod.id}>{prod.name}</p>)}*/}
                                        <Button onClick={() => this.showModal(order.prods)} variant='danger'>
                                            Szczegóły
                                        </Button>
                                    </th>
                                    <th>{order.bill}</th>
                                    <th>{order.paid_status}</th>
                                    <th>
                                        <Button onClick={() => this.changeOrderStatus(order.id)} variant='danger'>
                                            Opłać
                                        </Button>
                                    </th>
                                </tr>
                            ): null
                    }
                    </tbody>
                </Table>
                <OrderDetailsModal
                    id = {tempOrder}
                    show={modalShow}
                    onHide={() => this.setState({modalShow: false})}
                />
            </Container>
        );
    }
}
