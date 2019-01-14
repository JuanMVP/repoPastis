import request from 'supertest'
import { apiRoot } from '../../config'
import express from '../../services/express'
import routes, { Medicamento } from '.'

const app = () => express(apiRoot, routes)

let medicamento

beforeEach(async () => {
  medicamento = await Medicamento.create({})
})

test('POST /medicamentos 201', async () => {
  const { status, body } = await request(app())
    .post(`${apiRoot}`)
    .send({ nombre: 'test', cantidad: 'test', gramos: 'test' })
  expect(status).toBe(201)
  expect(typeof body).toEqual('object')
  expect(body.nombre).toEqual('test')
  expect(body.cantidad).toEqual('test')
  expect(body.gramos).toEqual('test')
})

test('GET /medicamentos 200', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}`)
  expect(status).toBe(200)
  expect(Array.isArray(body.rows)).toBe(true)
  expect(Number.isNaN(body.count)).toBe(false)
})

test('GET /medicamentos/:id 200', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}/${medicamento.id}`)
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(medicamento.id)
})

test('GET /medicamentos/:id 404', async () => {
  const { status } = await request(app())
    .get(apiRoot + '/123456789098765432123456')
  expect(status).toBe(404)
})

test('PUT /medicamentos/:id 200', async () => {
  const { status, body } = await request(app())
    .put(`${apiRoot}/${medicamento.id}`)
    .send({ nombre: 'test', cantidad: 'test', gramos: 'test' })
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(medicamento.id)
  expect(body.nombre).toEqual('test')
  expect(body.cantidad).toEqual('test')
  expect(body.gramos).toEqual('test')
})

test('PUT /medicamentos/:id 404', async () => {
  const { status } = await request(app())
    .put(apiRoot + '/123456789098765432123456')
    .send({ nombre: 'test', cantidad: 'test', gramos: 'test' })
  expect(status).toBe(404)
})

test('DELETE /medicamentos/:id 204', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${medicamento.id}`)
  expect(status).toBe(204)
})

test('DELETE /medicamentos/:id 404', async () => {
  const { status } = await request(app())
    .delete(apiRoot + '/123456789098765432123456')
  expect(status).toBe(404)
})
