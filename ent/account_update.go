// Code generated by ent, DO NOT EDIT.

package ent

import (
	"context"
	"errors"
	"fmt"

	"entgo.io/ent/dialect/sql"
	"entgo.io/ent/dialect/sql/sqlgraph"
	"entgo.io/ent/schema/field"
	"github.com/hosppy/oxcoding/ent/account"
	"github.com/hosppy/oxcoding/ent/predicate"
)

// AccountUpdate is the builder for updating Account entities.
type AccountUpdate struct {
	config
	hooks    []Hook
	mutation *AccountMutation
}

// Where appends a list predicates to the AccountUpdate builder.
func (au *AccountUpdate) Where(ps ...predicate.Account) *AccountUpdate {
	au.mutation.Where(ps...)
	return au
}

// SetEmail sets the "email" field.
func (au *AccountUpdate) SetEmail(s string) *AccountUpdate {
	au.mutation.SetEmail(s)
	return au
}

// SetName sets the "name" field.
func (au *AccountUpdate) SetName(s string) *AccountUpdate {
	au.mutation.SetName(s)
	return au
}

// SetNillableName sets the "name" field if the given value is not nil.
func (au *AccountUpdate) SetNillableName(s *string) *AccountUpdate {
	if s != nil {
		au.SetName(*s)
	}
	return au
}

// SetPhoneNumber sets the "phone_number" field.
func (au *AccountUpdate) SetPhoneNumber(s string) *AccountUpdate {
	au.mutation.SetPhoneNumber(s)
	return au
}

// SetActive sets the "active" field.
func (au *AccountUpdate) SetActive(b bool) *AccountUpdate {
	au.mutation.SetActive(b)
	return au
}

// SetNillableActive sets the "active" field if the given value is not nil.
func (au *AccountUpdate) SetNillableActive(b *bool) *AccountUpdate {
	if b != nil {
		au.SetActive(*b)
	}
	return au
}

// SetPasswordHash sets the "password_hash" field.
func (au *AccountUpdate) SetPasswordHash(s string) *AccountUpdate {
	au.mutation.SetPasswordHash(s)
	return au
}

// SetNillablePasswordHash sets the "password_hash" field if the given value is not nil.
func (au *AccountUpdate) SetNillablePasswordHash(s *string) *AccountUpdate {
	if s != nil {
		au.SetPasswordHash(*s)
	}
	return au
}

// ClearPasswordHash clears the value of the "password_hash" field.
func (au *AccountUpdate) ClearPasswordHash() *AccountUpdate {
	au.mutation.ClearPasswordHash()
	return au
}

// SetAvatarURL sets the "avatar_url" field.
func (au *AccountUpdate) SetAvatarURL(s string) *AccountUpdate {
	au.mutation.SetAvatarURL(s)
	return au
}

// SetNillableAvatarURL sets the "avatar_url" field if the given value is not nil.
func (au *AccountUpdate) SetNillableAvatarURL(s *string) *AccountUpdate {
	if s != nil {
		au.SetAvatarURL(*s)
	}
	return au
}

// ClearAvatarURL clears the value of the "avatar_url" field.
func (au *AccountUpdate) ClearAvatarURL() *AccountUpdate {
	au.mutation.ClearAvatarURL()
	return au
}

// Mutation returns the AccountMutation object of the builder.
func (au *AccountUpdate) Mutation() *AccountMutation {
	return au.mutation
}

// Save executes the query and returns the number of nodes affected by the update operation.
func (au *AccountUpdate) Save(ctx context.Context) (int, error) {
	return withHooks(ctx, au.sqlSave, au.mutation, au.hooks)
}

// SaveX is like Save, but panics if an error occurs.
func (au *AccountUpdate) SaveX(ctx context.Context) int {
	affected, err := au.Save(ctx)
	if err != nil {
		panic(err)
	}
	return affected
}

// Exec executes the query.
func (au *AccountUpdate) Exec(ctx context.Context) error {
	_, err := au.Save(ctx)
	return err
}

// ExecX is like Exec, but panics if an error occurs.
func (au *AccountUpdate) ExecX(ctx context.Context) {
	if err := au.Exec(ctx); err != nil {
		panic(err)
	}
}

func (au *AccountUpdate) sqlSave(ctx context.Context) (n int, err error) {
	_spec := sqlgraph.NewUpdateSpec(account.Table, account.Columns, sqlgraph.NewFieldSpec(account.FieldID, field.TypeInt))
	if ps := au.mutation.predicates; len(ps) > 0 {
		_spec.Predicate = func(selector *sql.Selector) {
			for i := range ps {
				ps[i](selector)
			}
		}
	}
	if value, ok := au.mutation.Email(); ok {
		_spec.SetField(account.FieldEmail, field.TypeString, value)
	}
	if value, ok := au.mutation.Name(); ok {
		_spec.SetField(account.FieldName, field.TypeString, value)
	}
	if value, ok := au.mutation.PhoneNumber(); ok {
		_spec.SetField(account.FieldPhoneNumber, field.TypeString, value)
	}
	if value, ok := au.mutation.Active(); ok {
		_spec.SetField(account.FieldActive, field.TypeBool, value)
	}
	if value, ok := au.mutation.PasswordHash(); ok {
		_spec.SetField(account.FieldPasswordHash, field.TypeString, value)
	}
	if au.mutation.PasswordHashCleared() {
		_spec.ClearField(account.FieldPasswordHash, field.TypeString)
	}
	if value, ok := au.mutation.AvatarURL(); ok {
		_spec.SetField(account.FieldAvatarURL, field.TypeString, value)
	}
	if au.mutation.AvatarURLCleared() {
		_spec.ClearField(account.FieldAvatarURL, field.TypeString)
	}
	if n, err = sqlgraph.UpdateNodes(ctx, au.driver, _spec); err != nil {
		if _, ok := err.(*sqlgraph.NotFoundError); ok {
			err = &NotFoundError{account.Label}
		} else if sqlgraph.IsConstraintError(err) {
			err = &ConstraintError{msg: err.Error(), wrap: err}
		}
		return 0, err
	}
	au.mutation.done = true
	return n, nil
}

// AccountUpdateOne is the builder for updating a single Account entity.
type AccountUpdateOne struct {
	config
	fields   []string
	hooks    []Hook
	mutation *AccountMutation
}

// SetEmail sets the "email" field.
func (auo *AccountUpdateOne) SetEmail(s string) *AccountUpdateOne {
	auo.mutation.SetEmail(s)
	return auo
}

// SetName sets the "name" field.
func (auo *AccountUpdateOne) SetName(s string) *AccountUpdateOne {
	auo.mutation.SetName(s)
	return auo
}

// SetNillableName sets the "name" field if the given value is not nil.
func (auo *AccountUpdateOne) SetNillableName(s *string) *AccountUpdateOne {
	if s != nil {
		auo.SetName(*s)
	}
	return auo
}

// SetPhoneNumber sets the "phone_number" field.
func (auo *AccountUpdateOne) SetPhoneNumber(s string) *AccountUpdateOne {
	auo.mutation.SetPhoneNumber(s)
	return auo
}

// SetActive sets the "active" field.
func (auo *AccountUpdateOne) SetActive(b bool) *AccountUpdateOne {
	auo.mutation.SetActive(b)
	return auo
}

// SetNillableActive sets the "active" field if the given value is not nil.
func (auo *AccountUpdateOne) SetNillableActive(b *bool) *AccountUpdateOne {
	if b != nil {
		auo.SetActive(*b)
	}
	return auo
}

// SetPasswordHash sets the "password_hash" field.
func (auo *AccountUpdateOne) SetPasswordHash(s string) *AccountUpdateOne {
	auo.mutation.SetPasswordHash(s)
	return auo
}

// SetNillablePasswordHash sets the "password_hash" field if the given value is not nil.
func (auo *AccountUpdateOne) SetNillablePasswordHash(s *string) *AccountUpdateOne {
	if s != nil {
		auo.SetPasswordHash(*s)
	}
	return auo
}

// ClearPasswordHash clears the value of the "password_hash" field.
func (auo *AccountUpdateOne) ClearPasswordHash() *AccountUpdateOne {
	auo.mutation.ClearPasswordHash()
	return auo
}

// SetAvatarURL sets the "avatar_url" field.
func (auo *AccountUpdateOne) SetAvatarURL(s string) *AccountUpdateOne {
	auo.mutation.SetAvatarURL(s)
	return auo
}

// SetNillableAvatarURL sets the "avatar_url" field if the given value is not nil.
func (auo *AccountUpdateOne) SetNillableAvatarURL(s *string) *AccountUpdateOne {
	if s != nil {
		auo.SetAvatarURL(*s)
	}
	return auo
}

// ClearAvatarURL clears the value of the "avatar_url" field.
func (auo *AccountUpdateOne) ClearAvatarURL() *AccountUpdateOne {
	auo.mutation.ClearAvatarURL()
	return auo
}

// Mutation returns the AccountMutation object of the builder.
func (auo *AccountUpdateOne) Mutation() *AccountMutation {
	return auo.mutation
}

// Where appends a list predicates to the AccountUpdate builder.
func (auo *AccountUpdateOne) Where(ps ...predicate.Account) *AccountUpdateOne {
	auo.mutation.Where(ps...)
	return auo
}

// Select allows selecting one or more fields (columns) of the returned entity.
// The default is selecting all fields defined in the entity schema.
func (auo *AccountUpdateOne) Select(field string, fields ...string) *AccountUpdateOne {
	auo.fields = append([]string{field}, fields...)
	return auo
}

// Save executes the query and returns the updated Account entity.
func (auo *AccountUpdateOne) Save(ctx context.Context) (*Account, error) {
	return withHooks(ctx, auo.sqlSave, auo.mutation, auo.hooks)
}

// SaveX is like Save, but panics if an error occurs.
func (auo *AccountUpdateOne) SaveX(ctx context.Context) *Account {
	node, err := auo.Save(ctx)
	if err != nil {
		panic(err)
	}
	return node
}

// Exec executes the query on the entity.
func (auo *AccountUpdateOne) Exec(ctx context.Context) error {
	_, err := auo.Save(ctx)
	return err
}

// ExecX is like Exec, but panics if an error occurs.
func (auo *AccountUpdateOne) ExecX(ctx context.Context) {
	if err := auo.Exec(ctx); err != nil {
		panic(err)
	}
}

func (auo *AccountUpdateOne) sqlSave(ctx context.Context) (_node *Account, err error) {
	_spec := sqlgraph.NewUpdateSpec(account.Table, account.Columns, sqlgraph.NewFieldSpec(account.FieldID, field.TypeInt))
	id, ok := auo.mutation.ID()
	if !ok {
		return nil, &ValidationError{Name: "id", err: errors.New(`ent: missing "Account.id" for update`)}
	}
	_spec.Node.ID.Value = id
	if fields := auo.fields; len(fields) > 0 {
		_spec.Node.Columns = make([]string, 0, len(fields))
		_spec.Node.Columns = append(_spec.Node.Columns, account.FieldID)
		for _, f := range fields {
			if !account.ValidColumn(f) {
				return nil, &ValidationError{Name: f, err: fmt.Errorf("ent: invalid field %q for query", f)}
			}
			if f != account.FieldID {
				_spec.Node.Columns = append(_spec.Node.Columns, f)
			}
		}
	}
	if ps := auo.mutation.predicates; len(ps) > 0 {
		_spec.Predicate = func(selector *sql.Selector) {
			for i := range ps {
				ps[i](selector)
			}
		}
	}
	if value, ok := auo.mutation.Email(); ok {
		_spec.SetField(account.FieldEmail, field.TypeString, value)
	}
	if value, ok := auo.mutation.Name(); ok {
		_spec.SetField(account.FieldName, field.TypeString, value)
	}
	if value, ok := auo.mutation.PhoneNumber(); ok {
		_spec.SetField(account.FieldPhoneNumber, field.TypeString, value)
	}
	if value, ok := auo.mutation.Active(); ok {
		_spec.SetField(account.FieldActive, field.TypeBool, value)
	}
	if value, ok := auo.mutation.PasswordHash(); ok {
		_spec.SetField(account.FieldPasswordHash, field.TypeString, value)
	}
	if auo.mutation.PasswordHashCleared() {
		_spec.ClearField(account.FieldPasswordHash, field.TypeString)
	}
	if value, ok := auo.mutation.AvatarURL(); ok {
		_spec.SetField(account.FieldAvatarURL, field.TypeString, value)
	}
	if auo.mutation.AvatarURLCleared() {
		_spec.ClearField(account.FieldAvatarURL, field.TypeString)
	}
	_node = &Account{config: auo.config}
	_spec.Assign = _node.assignValues
	_spec.ScanValues = _node.scanValues
	if err = sqlgraph.UpdateNode(ctx, auo.driver, _spec); err != nil {
		if _, ok := err.(*sqlgraph.NotFoundError); ok {
			err = &NotFoundError{account.Label}
		} else if sqlgraph.IsConstraintError(err) {
			err = &ConstraintError{msg: err.Error(), wrap: err}
		}
		return nil, err
	}
	auo.mutation.done = true
	return _node, nil
}
