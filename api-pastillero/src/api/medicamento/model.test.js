import { Medicamento } from '.'

let medicamento

beforeEach(async () => {
  medicamento = await Medicamento.create({ nombre: 'test', cantidad: 'test', gramos: 'test' })
})

describe('view', () => {
  it('returns simple view', () => {
    const view = medicamento.view()
    expect(typeof view).toBe('object')
    expect(view.id).toBe(medicamento.id)
    expect(view.nombre).toBe(medicamento.nombre)
    expect(view.cantidad).toBe(medicamento.cantidad)
    expect(view.gramos).toBe(medicamento.gramos)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })

  it('returns full view', () => {
    const view = medicamento.view(true)
    expect(typeof view).toBe('object')
    expect(view.id).toBe(medicamento.id)
    expect(view.nombre).toBe(medicamento.nombre)
    expect(view.cantidad).toBe(medicamento.cantidad)
    expect(view.gramos).toBe(medicamento.gramos)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })
})
