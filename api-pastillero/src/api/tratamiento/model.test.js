import { Tratamiento } from '.'

let tratamiento

beforeEach(async () => {
  tratamiento = await Tratamiento.create({ periodo_toma: 'test', fecha_inicio: 'test', fecha_final: 'test' })
})

describe('view', () => {
  it('returns simple view', () => {
    const view = tratamiento.view()
    expect(typeof view).toBe('object')
    expect(view.id).toBe(tratamiento.id)
    expect(view.periodo_toma).toBe(tratamiento.periodo_toma)
    expect(view.fecha_inicio).toBe(tratamiento.fecha_inicio)
    expect(view.fecha_final).toBe(tratamiento.fecha_final)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })

  it('returns full view', () => {
    const view = tratamiento.view(true)
    expect(typeof view).toBe('object')
    expect(view.id).toBe(tratamiento.id)
    expect(view.periodo_toma).toBe(tratamiento.periodo_toma)
    expect(view.fecha_inicio).toBe(tratamiento.fecha_inicio)
    expect(view.fecha_final).toBe(tratamiento.fecha_final)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })
})
