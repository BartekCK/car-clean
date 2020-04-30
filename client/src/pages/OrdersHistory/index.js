import React from 'react';
import {Button, Col, Container, InputGroup, Row, Table} from 'react-bootstrap';
import {SingleInputOrder} from "../../components/Order";
import BasketImage from "../../resources/img/basket.png";
import {getUserOrderProducts} from "../../helpers/apiCommands";

export class OrderHistory extends React.Component {
    state = {
        orders: []
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
    };

    render() {
        const { orders } = this.state;

        return (
            <Container className='shadow'>
                <h1>Historia zamówień</h1>
                <Table striped bordered hover>
                    <thead>
                    <tr>
                        <th>ID zamówienia</th>
                        <th>Zamówione produkty</th>
                        <th>Kwota do zapłaty</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        orders.length?
                            orders.map(order =>
                                <tr key={order.id}>
                                    <th>{order.id}</th>
                                    <th>
                                        {order.prods.map(prod => <p key={prod.id}>{prod.name}</p>)}
                                    </th>
                                    <th>{order.bill}</th>
                                    <th>{order.paid_status}<br/>
                                        <Button onClick={() => this.changeOrderStatus(order.id)} variant='danger'>
                                            Opłać
                                        </Button>
                                    </th>
                                </tr>
                            ): null
                    }
                    </tbody>
                </Table>
            </Container>
        );
    }
}
