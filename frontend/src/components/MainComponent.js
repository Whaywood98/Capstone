import React, { Component } from 'react';
import Navbar from './NavbarComponent';
import Footer from './FooterComponent';
import LoginPage from './LoginPageComponent';
import CreateAccount from './CreateAccountComponent';
import AboutUs from './AboutComponent';
import CreateUser from './CreateUserComponent';
import { Route, Redirect, Switch, withRouter } from 'react-router-dom';
import Home from './HomeComponent';
import { fetchSavingsAccount, fetchCheckingAccounts, fetchCdAccounts, fetchPersonalCheckingAccount, fetchDbaCheckingAccounts, fetchUser, postUser, addUser } from '../redux/ActionCreators';
import { actions, Form } from 'react-redux-form'
import { connect } from 'react-redux';
import UserServices from '../services/UserServices';
import AccountSummary from './AccountSummaryComponent';


const mapStateToProps = state => {
    return {
        user: state.user
    }
}

const mapDispatchToProps = (dispatch) => ({
    addUser: () => dispatch(addUser())
});


class Main extends Component {
    
    constructor(props) {
        super(props);

    }

    render() {

        const HomePage = () => {
            return(
                <Home user={this.props.user}/>
            );
        }

        return(
            <div>
                <Navbar/>
                <Switch>
                    <Route path='/home' component={HomePage} />
                    <Route path='/aboutus' component={() => <AboutUs />} />
                    <Route path='/signin' component={() => <LoginPage addUser={this.props.addUser}/>} />
                    <Route path='/createaccount' component={() => <CreateAccount user={this.props.user}/>} />
                    <Route path='/register' component={() => <CreateUser /> }/>
                    <Route path='/accountsummary/checking' component={() => <AccountSummary accountType="Checking Accounts" accounts={this.props.user.checkingAccounts} />} />
                    <Route path='/accountsummary/savings' component={() => <AccountSummary accountType="Savings Account" accounts={this.props.user.savingsAccount} />} />
                    <Route path='/accountsummary/personal' component={() => <AccountSummary accountType="Personal Checking Account" accounts={this.props.user.personalCheckingAccount} />} />
                    <Route path='/accountsummary/cd' component={() => <AccountSummary accountType="Certificate of Deposit Accounts" accounts={this.props.user.cdAccounts} />} />
                    <Route path='/accountsummary/dba' component={() => <AccountSummary accountType="DBA Checking Accounts" accounts={this.props.user.dbaAccounts} />} />
                    <Route path='/accountsummary/regular' component={() => <AccountSummary accountType="Regular IRA" accounts={this.props.user.regularIra} />} />
                    <Route path='/accountsummary/rollover' component={() => <AccountSummary accountType="Rollover IRA" accounts={this.props.user.rolloverIra} />} />
                    <Route path='/accountsummary/roth' component={() => <AccountSummary accountType="Roth IRA" accounts={this.props.user.rothIra} />} />
                </Switch>
                <Footer/>
            </div>
        );
    }
}

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(Main));